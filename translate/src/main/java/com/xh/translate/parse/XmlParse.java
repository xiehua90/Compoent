package com.xh.translate.parse;


import com.xh.translate.PageConfig;
import com.xh.translate.bean.Page;
import com.xh.translate.bean.Word;
import com.xh.translate.bean.XmlPair;
import com.xh.translate.utils.DateUtils;
import com.xh.translate.utils.FileUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XmlParse implements Parse {
    private final String xmlStringName = "strings.xml";
    private final String regex = "<\\s*string\\s+name=\\s*\"(.*?)\".*?>([\\s\\S]*?)</\\s*string\\s*>";
    private final String dirPrefix = "values";

    /**
     * @param resPath Android res文件夹的路径
     **/
    @Override
    public Page read(String resPath, PageConfig config) {
        Page page = new Page();
        List<String> locales = getLocalesFromRes(resPath, config);
        List<String> properties = page.getWordPropertyList();

        if (locales.isEmpty() || properties == null || properties.isEmpty()) {
            return page;
        }

        properties.addAll(locales);
        Map<String, Integer> colName = new HashMap<>();
        for (int i = 0; i < properties.size(); i++) {
            colName.put(properties.get(i), i);
        }
        page.setColumnName(colName, config);

        List<String> inLocales = page.getLocaleList();
        if (inLocales != null && !inLocales.isEmpty()) {
            String defaultLocales = getDefaultLocales(page, config);

            Map<String, List<XmlPair>> map = new HashMap<>();
            for (String lan : inLocales) {
                String dirName = lan.equals(defaultLocales) ? dirPrefix : dirPrefix + "-" + lan;
                String path = resPath + File.separator + dirName + File.separator + xmlStringName;
                map.put(lan, readXml(path));
            }
            page.setWord(pairsToWord(defaultLocales, map));
        }
        return page;
    }

    @Override
    public void write(String path, Page page, PageConfig config) {
        Map<String, String> map = getWriteLocaleMap(page, config);
        if (map == null) return;
        Map<String, List<XmlPair>> data = getAllXmlPairFromPage(page, new ArrayList<>(map.keySet()));

        for (String lan : data.keySet()) {
            writeXml(path + File.separator + map.get(lan), data.get(lan), config != null && config.isXmlWriteReplace());
        }
    }

    /**
     * @param isReplace true 则替换的id, 否则则忽略
     */
    private void writeXml(String dirPath, List<XmlPair> data, boolean isReplace) {
        if (data == null || dirPath == null) return;

        File file = new File(dirPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        file = new File(file, xmlStringName);

        String source = "";
        if (file.exists()) {
            source = FileUtils.readFile(file.getPath());
        }

        List<XmlPair> out = new ArrayList<>();

        if (source != null && !source.trim().equals("")) {
            for (XmlPair pair : data) {
                String regex = "<\\s*string\\s+name=\\s*\"" + pair.getKey() + "\".*?>([\\s\\S]*?)</\\s*string\\s*>";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(source);

                if (matcher.find()) {
                    if (isReplace) {
                        String s = matcher.group(0);
                        s = s.replace(">" + matcher.group(1) + "<", ">" + pair.getValue() + "<");
                        source = matcher.replaceFirst(s);
                    }
                    continue;
                }
                out.add(pair);
            }
        } else {
            out.addAll(data);
        }
        if (out.isEmpty()) return;

        StringBuilder builder = new StringBuilder();

        if (!source.contains("<resources>")) {
            builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<resources>\n");
        }

        builder.append(source.replace("</resources>", ""));

        String comment = String.format("<!--%s(%d)-->", DateUtils.getDate(), out.size());

        builder.append("\n" + comment + "\n");
        for (XmlPair pair : out) {
            builder.append("\t" + pair.toString() + "\n");
        }
        builder.append(comment + "\n\n");
        builder.append("</resources>");

        if (file.exists()) {
            file.delete();
        }

        try {
            FileWriter writer = new FileWriter(file);
            writer.write(builder.toString());
            writer.close();
            System.out.println(file.getPath() + " write over.");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private List<Word> pairsToWord(String defaultLocales, Map<String, List<XmlPair>> data) {
        if (data == null) return null;
        List<Word> words = new ArrayList<>();
        List<String> keys = new ArrayList<>();

        //根据默认语言的key依次添加
        List<XmlPair> pairs = data.get(defaultLocales);
        if (pairs != null) {
            for (XmlPair pair : pairs) {
                Word word = new Word();
                if (pair == null) continue;
                word.appendPair(pair, defaultLocales, true);
                words.add(word);
                keys.add(word.getKey());
            }
        }

        for (String locale : data.keySet()) {
            if (!locale.equals(defaultLocales)) {
                pairs = data.get(locale);
                if (pairs != null) {
                    for (XmlPair pair : pairs) {
                        if (pair == null) continue;
                        int index = keys.indexOf(pair.getKey());
                        Word word = index == -1 ? new Word() : words.get(index);
                        word.appendPair(pair, locale, false);
                        if (index == -1) {
                            words.add(word);
                            keys.add(word.getKey());
                        }
                    }
                }
            }
        }
        return words;
    }

    public List<String> getLocalesFromRes(String path, PageConfig config) {
        List<String> list = null;
        File res = new File(path);
        if (res.exists()) {
            list = new ArrayList<>();
            for (File dir : res.listFiles()) {
                if (dir.getName().contains(dirPrefix)) {
                    if (new File(dir, xmlStringName).exists()) {
                        String locales = dir.getName().replace("values-", "");
                        if (config != null) {
                            locales = locales.replace(dirPrefix, config.getDefaultLocalesColName());
                        }
                        list.add(locales);
                    }
                }
            }
        } else {
            throw new RuntimeException("File is not exist:" + path);
        }
        return list;
    }


    /**
     * @return Map.Entry entry, entry.getKey()为要输出的列,
     * entry.getValue()为要输出列所对应的文件夹名
     **/
    private Map<String, String> getWriteLocaleMap(Page page, PageConfig config) {
        if (page == null || page.getLocaleList() == null) return null;

        Map<String, String> map = new HashMap<>();
        List<String> locales = page.getLocaleList();

        if (!locales.isEmpty()) {
            List<String> outLocales;
            if (config != null && config.getOutColumnName() != null) {
                outLocales = new ArrayList<>();
                for (String lan : config.getOutColumnName()) {
                    int index = page.getColumnIndexIgnoreCase(lan);
                    if (index >= 0) {
                        outLocales.add(lan);
                    }
                }
            } else {
                outLocales = locales;
            }

            if (config != null && config.getOutLocalesDirName() != null) {
                for (int i = 0; i < outLocales.size(); i++) {
                    String lan = outLocales.get(i);
                    if (config.getOutLocalesDirName().length > i) {
                        map.put(lan, config.getOutLocalesDirName()[i]);
                    }
                }
            } else {
                List<String> outDir = getOutDir(outLocales, getDefaultLocales(page, config));
                for (int i = 0; i < outDir.size(); i++) {
                    map.put(outLocales.get(i), outDir.get(i));
                }
            }
        }
        return map;
    }


    private List<String> getOutDir(List<String> locales, String defaultLocale) {
        if (locales == null) return null;
        List<String> outDir = new ArrayList<>();

        int index = locales.indexOf(defaultLocale);

        String regex = "\\((.*?)\\)";
        Pattern pattern = Pattern.compile(regex);

        for (int i = 0; i < locales.size(); i++) {
            if (i == index) {
                outDir.add(dirPrefix);
                continue;
            }

            String locale = locales.get(i);
            if (locale != null) {
                Matcher matcher = pattern.matcher(locale);
                if (matcher.find()) {
                    outDir.add(dirPrefix + "-" + (matcher.group(1).length() < 4 ? matcher.group(1).toLowerCase() : matcher.group(1)));
                    continue;
                }
                outDir.add(dirPrefix + "-" + (locale.length() < 4 ? locale.toLowerCase() : locale));
            } else {
                outDir.add(null);
            }

        }
        return outDir;
    }


    private Map<String, List<XmlPair>> getAllXmlPairFromPage(Page page, List<String> limitLoc) {
        if (page == null) return null;

        List<String> locales = limitLoc != null ? limitLoc : page.getLocaleList();
        List<Word> words = page.getWord();

        Map<String, List<XmlPair>> map = new HashMap<>();

        if (locales != null && words != null) {
            for (String lan : locales) {
                List<XmlPair> data = new ArrayList<>();
                for (Word word : words) {
                    if (word != null) {
                        XmlPair pair = word.getXmlPair(lan);
                        if (pair != null && pair.isValidXmlPair()) {
                            data.add(pair);
                        }
                    }
                }
                map.put(lan, data);
            }
        }
        return map;
    }

    /**
     * 当config不为空时, 匹配与config.getDefaultLocalesColName 相符的语言作为默认语言
     * 否则locales.get(0)作为默认语言
     *
     * @return 默认语言, null表示无默认语言
     */
    private String getDefaultLocales(Page page, PageConfig config) {
        if (page == null) return null;
        List<String> locales = page.getLocaleList();
        if (locales == null || locales.isEmpty()) return null;

        if (config != null) {
            for (String lan : locales) {
                if (lan.equalsIgnoreCase(config.getDefaultLocalesColName())) {
                    return lan;
                }
            }
            for (String lan : locales) {
                String defaultName = config.getDefaultLocalesColName();
                if (lan.toLowerCase().contains(defaultName.toLowerCase())
                        || defaultName.toLowerCase().contains(lan.toLowerCase())) {
                    return lan;
                }
            }
        } else {
            for (String lan : locales) {
                if (lan.equals(dirPrefix)) {
                    return lan;
                }
            }
        }
        return null;
    }

    private List<XmlPair> readXml(String xmlPath) {
        String content = FileUtils.readFile(xmlPath);

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(content);

        String proRegex = "translatable\\s*=\"(.*)\"";
        Pattern proPattern = Pattern.compile(proRegex);

        List<XmlPair> list = new ArrayList<>();
        while (matcher.find()) {
            XmlPair pair = new XmlPair();
            String m = matcher.group(0);
            pair.setKey(matcher.group(1));
            pair.setValue(matcher.group(2));
            Matcher proMatcher = proPattern.matcher(m);
            if (proMatcher.find()) {
                pair.setTranslatable(Boolean.valueOf(proMatcher.group(1)));
            }
            list.add(pair);
        }
        return list;
    }

}
