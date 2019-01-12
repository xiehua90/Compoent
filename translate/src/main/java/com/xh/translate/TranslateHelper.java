package com.xh.translate;

import android.support.annotation.NonNull;

import com.xh.translate.bean.FileType;
import com.xh.translate.bean.Page;
import com.xh.translate.bean.Word;
import com.xh.translate.bean.XmlPair;
import com.xh.translate.parse.ExcelParse;
import com.xh.translate.parse.Parse;
import com.xh.translate.parse.TxtParse;
import com.xh.translate.parse.XmlParse;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class TranslateHelper {

    /**
     * xml, txt, xlsx 三种类型相互转换 其中xml文件路径为Android res的路径
     *
     * @param srcPath 输入文件路径, 文件类型需为xml, txt, xlsx其中一种
     * @param desPath 输出文件路径, 文件类型需为xml, txt, xlsx其中一种
     * @param config  置换配置，
     **/
    public static void convert(String srcPath, String desPath, PageConfig config) {
        Parse parse1 = createParse(srcPath);
        Parse parse2 = createParse(desPath);
        Page page = parse1.read(srcPath, config);
        showDuplicate(page, desPath, config);
        parse2.write(desPath, page, config);
    }

    /**
     * 根据各语言的值查询对应的key的值并写入
     *
     * @param srcPath   要查询key的语言的文件
     * @param referPath 被查询的文件的
     * @param config    配置参数，PageConfig.referLocale设置对比的语言
     */
    public static void insertKey(@NonNull String srcPath, @NonNull String referPath, @NonNull PageConfig config) {
        Parse parse = createParse(srcPath);

        Page page1 = parse.read(srcPath, config);
        Page page2 = createParse(referPath).read(referPath, config);

        List<Word> list1 = page1.getWord();
        List<Word> list2 = page2.getWord();

        page1.insertKey(config);

        if (list1 != null && list2 != null) {
            for (Word word : list1) {
                if (word == null) continue;
                XmlPair pair = word.getXmlPair(config.getReferLocale());
                if (pair == null) continue;
                String v = pair.getValue();
                if (v != null) {
                    List<Word> tem = page2.searchWordIgnoreSpecChByValue(v, config.getReferLocale());
                    if (tem != null && !tem.isEmpty()) {
                        word.setKey(tem.get(0).getKey());
                    }
                }
            }
        }
        parse.write(srcPath, page1, config);
    }

    /**
     * 查询原来项目中已有的翻译字段
     *
     * @param srcPath   需要找查找翻译的文件;
     * @param referPath 旧项目的文件路径;
     * @param desPath   输出文件路径
     * @param config    配置参数，PageConfig.referLocale设置对比的语言
     */
    public static void searchOldTranslate(@NonNull String srcPath, @NonNull String referPath, @NonNull String desPath, @NonNull PageConfig config) {
        Parse parse = createParse(srcPath);

        Page page1 = parse.read(srcPath, config);
        Page page2 = createParse(referPath).read(referPath, config);

        List<Word> list1 = page1.getWord();
        List<Word> list2 = page2.getWord();

        Page page3 = new Page();
        page3.setColumnName(page2.getColumnName(), config);
        page3.insertKey(config);
        List<Word> array = new ArrayList<>();

        if (list1 != null && list2 != null) {
            for (Word word : list1) {
                if (word == null || !word.isTranslatable()) continue;
                XmlPair pair = word.getXmlPair(config.getReferLocale());
                if (pair == null) continue;
                String v = pair.getValue();
                if (v !=null){
                    List<Word> tem = page2.searchWordIgnoreSpecChByValue(v, config.getReferLocale());
                    if (tem != null && !tem.isEmpty()) {
                        tem.get(0).setKey(word.getKey());
                        array.add(tem.get(0));
                    }
                }
            }
        }
        page3.setWord(array);
        createParse(desPath).write(desPath, page3, config);
    }

    private static void showDuplicate(Page page, String desPath, PageConfig config) {
        if (page == null || config == null || config.getDuplicateLocale() == null) return;
        String[] duplicateCol = config.getDuplicateLocale();

        List<XmlPair> all = new ArrayList<>();
        //控制台输出
        for (String lan : duplicateCol) {
            if (lan != null && !lan.trim().equals("")) {
                List<List<Word>> list = page.getAllDuplicateWordIgnoreSpecCh(lan);
                if (list == null) return;
                System.out.println("<--!" + lan + " 重复字段-->");
                for (List<Word> l : list) {
                    if (l == null) continue;
                    for (Word w : l) {
                        if (w == null) continue;
                        XmlPair pair = w.getXmlPair(lan);
                        if (pair != null) {
                            all.add(pair);
                            System.out.println(pair.toString());
                        }
                    }
                }
                System.out.println("<--!" + lan + " 重复字段 over-->");
            }
        }

        //文件输出
        FileType type = FileType.newInstance(desPath);
        File parent = type.equals(FileType.DIR) ? new File(desPath) : new File(desPath).getParentFile();

        File file = new File(parent, "duplicate_" + getDate() + ".xml");
        try {
            FileWriter writer = new FileWriter(file);
            for (XmlPair pair : all) {
                writer.write(pair.toString() + "\n");
            }
            writer.flush();
            writer.close();
            System.out.println("write over:" + file.getPath());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private static Parse createParse(String filePath) {
        FileType fileType = FileType.newInstance(filePath);
        if (fileType.equals(FileType.UNKNOWN)) {
            throw new RuntimeException("File type Error:" + filePath);
        }

        switch (fileType) {
            case DIR:
                return new XmlParse();
            case EXCEL:
                return new ExcelParse();
            default:
                return new TxtParse();
        }
    }

    public static String getDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        return format.format(new Date());
    }


}
