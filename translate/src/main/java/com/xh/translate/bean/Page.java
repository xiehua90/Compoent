package com.xh.translate.bean;


import android.support.annotation.NonNull;

import com.xh.translate.PageConfig;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Page {
    private Map<String, Integer> columnName;
    private List<String> property;
    private List<String> locale;
    private List<Word> word;

    public Map<String, Integer> getColumnName() {
        return columnName;
    }

    public void setColumnName(Map<String, Integer> columnName, PageConfig config) {
        this.columnName = columnName;
        if (columnName == null) return;
        locale = new ArrayList<>();
        property = new ArrayList<>();
        List<Integer> indexRecord = new ArrayList<>();

        for (String col : columnName.keySet()) {
            String proName = getWordPropertyName(col);
            if (proName == null) {
                if (config != null && config.getInLocale() != null && !hasLocale(config.getInLocale(), col)) {
                    continue;
                }
                indexRecord.add(columnName.get(col));
//                locale.add(col);
            } else {
                property.add(proName);
            }
        }

        Collections.sort(indexRecord);
        for (int i : indexRecord) {
            for (String col : columnName.keySet()) {
                if (columnName.get(col) == i) {
                    locale.add(col);
                }
            }
        }


//        if (!property.contains("key")) {
//            throw new NumberFormatException("Unable to find the key column");
//        }
    }

    public int getColumnIndexIgnoreCase(String colName) {
        return getColumnIndexIgnoreCase(columnName, colName);
    }

    public int getColumnIndexIgnoreCase(Map<String, Integer> columnMap, String colName) {
        if (columnMap != null && colName != null) {

            //先判断相等
            for (Map.Entry<String, Integer> entry : columnMap.entrySet()) {
                String name = entry.getKey().toLowerCase();
                if (name.equalsIgnoreCase(colName)) {
                    return entry.getValue();
                }
            }

            //模糊判断
            for (Map.Entry<String, Integer> entry : columnMap.entrySet()) {
                String name = entry.getKey().toLowerCase();
                if (name.toLowerCase().contains(colName.toLowerCase())) {
                    return entry.getValue();
                }
                if (colName.toLowerCase().contains(name.toLowerCase())) {
                    return entry.getValue();
                }
            }
        }
        return -1;
    }


    public String getWordPropertyName(String str) {
        if (str == null || str.trim().equals("")) return null;
        List<String> list = getWordPropertyList();
        if (list != null) {
            String colName = str.toLowerCase().trim();
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
        if (locale == null || locale.isEmpty()) return null;
        return locale.get(0);
    }


    public List<String> getProperty() {
        return property;
    }


    public List<String> getLocale() {
        return locale;
    }


    public List<Word> getWord() {
        return word;
    }

    public void setWord(List<Word> word) {
        this.word = word;
    }

    public void insertKey(PageConfig config) {
        if (property != null && !property.contains("key")) {
            for (String key : columnName.keySet()) {
                columnName.put(key, columnName.get(key) + 1);
            }
            columnName.put("key", 0);
            setColumnName(columnName, config);
        }
    }

    public List<Word> searchWordsByKey(@NonNull String key) {
        if (word == null) return null;
        if (key.trim().equals("")) return null;

        List<Word> array = new ArrayList<>();

        for (Word w : word) {
            if (key.equals(w.getKey())) {
                array.add(w);
            }
        }

        return array;
    }

    public List<Word> searWordIgnoreCaseByValue(@NonNull String value, @NonNull String locale) {
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


    private String[] specialChar = new String[]{".", "。",
            ",", "，",
            "?", "？",
            "!", "！",
            ":", "：",
            "\t", "\r", "\n"};

    public List<Word> searchWordIgnoreSpecChByValue(@NonNull String value, @NonNull String locale) {
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

    public List<List<Word>> getAllDuplicateWordIgnoreSpecCh(@NonNull String locale) {
        if (word == null || locale.trim().equals("")) return null;
        List<List<Word>> array = new ArrayList<>();
        List<String> record = new ArrayList<>();
        for (Word w : word) {
            if (w != null && w.getXmlPair(locale) != null) {
                XmlPair pair = w.getXmlPair(locale);
                if (pair.getValue() != null) {
                    String value = filterSpecialChar(pair.getValue());
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
