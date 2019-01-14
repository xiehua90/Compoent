package com.xh.translate.bean;

import com.xh.translate.PageConfig;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Page {
    private Map<String, Integer> columnNameMap;
    private List<String> propertyList;
    private List<String> localeList;
    private List<Word> word;

    public Map<String, Integer> getColumnNameMap() {
        return columnNameMap;
    }

    public void setColumnName(Map<String, Integer> columnName, PageConfig config) {
        if (columnName == null) return;
        this.columnNameMap = columnName;
        localeList = new ArrayList<>();
        propertyList = new ArrayList<>();
        List<Integer> indexRecord = new ArrayList<>();
        for (String col : columnName.keySet()) {
            String proName = getWordPropertyName(col);
            if (proName == null) {
                if (config != null && config.getInLocale() != null && !hasLocale(config.getInLocale(), col)) {
                    continue;
                }
                indexRecord.add(columnName.get(col));
//                localeList.add(col);
            } else {
                propertyList.add(proName);
            }
        }

        Collections.sort(indexRecord);
        for (int i : indexRecord) {
            for (String col : columnName.keySet()) {
                if (columnName.get(col) == i) {
                    localeList.add(col);
                }
            }
        }

//        if (!propertyList.contains("key")) {
//            throw new NumberFormatException("Unable to find the key column");
//        }
    }

    public int getColumnIndexIgnoreCase(String colName) {
        return getColumnIndexIgnoreCase(columnNameMap, colName);
    }

    public int getColumnIndexIgnoreCase(Map<String, Integer> columnMap, String colName) {
        if (columnMap != null && colName != null) {
            String name = findColumnNameIgnoreCase(columnMap, colName);
            if (name != null && columnMap.get(name) != null) {
                return columnMap.get(name);
            }
        }
        return -1;
    }

    public String getColumnNameIgnoreCase(String colName) {
        return findColumnNameIgnoreCase(columnNameMap, colName);
    }

    private String findColumnNameIgnoreCase(Map<String, Integer> columnMap, String colName) {
        if (columnMap != null && colName != null) {

            //先判断相等
            for (Map.Entry<String, Integer> entry : columnMap.entrySet()) {
                String name = entry.getKey();
                if (name.equals(colName)) {
                    return name;
                }
            }

            //模糊判断
            for (Map.Entry<String, Integer> entry : columnMap.entrySet()) {
                String name = entry.getKey();
                if (name.trim().toLowerCase().contains(colName.trim().toLowerCase())) {
                    return name;
                }
                if (colName.trim().toLowerCase().contains(name.trim().toLowerCase())) {
                    return name;
                }
            }
        }
        return null;
    }


    private String getWordPropertyName(String str) {
        if (str == null || str.trim().equals("")) return null;
        List<String> list = getWordPropertyList();
        if (list != null) {
            String colName = str.trim().toLowerCase();
            for (String proName : list) {
                if (colName.contains(proName)) {
                    return proName;
                }
            }
        }
        return null;
    }

    public List<String> getWordPropertyList() {
        try {
            Class c = Class.forName("com.xh.translate.bean.Word");
            List<String> list = new ArrayList<>();
            for (Field f : c.getDeclaredFields()) {
                String proName = f.getName();
                if (!proName.equals("values")) {
                    list.add(proName);
                }
            }
            return list;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean hasLocale(String[] array, String locale) {
        if (array == null || locale == null || locale.trim().equals("")) return false;
        for (String s : array) {
            if (locale.toLowerCase().contains(s.trim().toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public String getFirstColumnLocale() {
        if (localeList == null || localeList.isEmpty()) return null;
        return localeList.get(0);
    }


    public List<String> getPropertyList() {
        return propertyList;
    }

    public List<String> getLocaleList() {
        return localeList;
    }


    public List<Word> getWord() {
        return word;
    }

    public void setWord(List<Word> word) {
        this.word = word;
    }

    public void insertKey(PageConfig config) {
        if (propertyList != null && !propertyList.contains("key")) {
            for (String key : columnNameMap.keySet()) {
                columnNameMap.put(key, columnNameMap.get(key) + 1);
            }
            columnNameMap.put("key", 0);
            setColumnName(columnNameMap, config);
        }
    }

    public List<Word> searchWordsByKey(String key) {
        if (word == null) return null;
        if (key == null || key.trim().equals("")) return null;

        List<Word> array = new ArrayList<>();

        for (Word w : word) {
            if (key.equals(w.getKey())) {
                array.add(w);
            }
        }

        return array;
    }

    public List<Word> searWordIgnoreCaseByValue(String value, String locale) {
        if (value == null || locale == null) return null;
        if (word == null || value.trim().equals("") || locale.trim().equals("")) return null;
        List<Word> array = new ArrayList<>();
        for (Word w : word) {
            XmlPair pair = w.getXmlPair(locale);
            if (pair != null && pair.getValue() != null) {
                if (value.trim().equalsIgnoreCase(pair.getValue().trim())) {
                    array.add(w);
                }
            }
        }
        return array;
    }

    private final String[] specialChar = new String[]{".", "。",
            ",", "，",
            "?", "？",
            "!", "！",
            ":", "：",
            "\r", "\n"};

    public List<Word> searchWordIgnoreSpecChByValue(String value, String locale) {
        if (value == null || locale == null) return null;
        if (word == null || value.trim().equals("") || locale.trim().equals("")) return null;
        List<Word> array = new ArrayList<>();
        for (Word w : word) {
            XmlPair pair = w.getXmlPair(locale);
            if (pair != null && pair.getValue() != null) {
                if (equalIgnoreSpecialChar(value, pair.getValue())) {
                    array.add(w);
                }
            }
        }
        return array;
    }

    public List<List<Word>> getAllDuplicateWordIgnoreSpecCh(String locale) {
        if (locale == null) return null;
        if (word == null || locale.trim().equals("")) return null;
        List<List<Word>> array = new ArrayList<>();
        List<String> record = new ArrayList<>();
        for (Word w : word) {
            if (w != null && w.getXmlPair(locale) != null) {
                XmlPair pair = w.getXmlPair(locale);
                if (pair.getValue() != null) {
                    String value = filterSpecialChar(pair.getValue()).trim().toLowerCase();
                    if (!record.contains(value)) {
                        List<Word> tem = searchWordIgnoreSpecChByValue(value, locale);
                        if (tem != null && tem.size() > 1) {
                            record.add(value);
                            array.add(tem);
                        }
                    }
                }
            }
        }
        return array;
    }


    private String filterSpecialChar(String str) {
        if (str == null) return null;
        for (String c : specialChar) {
            str = str.replace(c, "");
        }
        return str;
    }

    private boolean equalIgnoreSpecialChar(String s1, String s2) {
        if (s1 == null) {
            return s2 == null;
        }
        if (s2 == null) return false;

        s1 = filterSpecialChar(s1);
        s2 = filterSpecialChar(s2);

        return s1.trim().equalsIgnoreCase(s2.trim());
    }

}
