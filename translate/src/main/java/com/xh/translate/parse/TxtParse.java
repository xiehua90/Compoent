package com.xh.translate.parse;



import com.xh.translate.PageConfig;
import com.xh.translate.bean.Page;
import com.xh.translate.bean.Word;
import com.xh.translate.utils.FileUtils;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TxtParse implements Parse {
    private final String TAB = "\t";

    @Override
    public Page read(String path, PageConfig config) {
        String[] lines = FileUtils.readFile(path).split("\n");
        Page page = new Page();
        Map<String, Integer> colName = new HashMap<>();
        int firstVisibleLine = 0;

        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            if (!line.trim().equals("")) {
                firstVisibleLine = i;
                String[] array = line.split(TAB);
                for (int j = 0; j < array.length; j++) {
                    String value = array[j];
                    if (!value.trim().equals("")) {
                        if (colName.keySet().contains(value.trim())) {
                            throw new NumberFormatException("Cannot exist the same column name \'" + value.trim() + "\' in Excel: " + path);
                        }
                        colName.put(value.trim(), j);
                    }
                }
                break;
            }
        }

        page.setColumnName(colName, config);

        if (page.getLocaleList() != null && !page.getLocaleList().isEmpty()) {
            List<Word> words = new ArrayList<>();
            for (int i = firstVisibleLine + 1; i < lines.length; i++) {
                String line = lines[i];
                if (line.trim().equals("")) continue;
                String[] array = line.split(TAB);
                try {
                    Class c = Class.forName("com.xh.translate.bean.Word");
                    Object ob = c.newInstance();
                    for (String name : page.getPropertyList()) {
                        int colIndex = page.getColumnIndexIgnoreCase(name);
                        if (array.length > colIndex) {
                            String value = array[colIndex].trim();
                            if (!value.equals("")) {
                                Field field = c.getDeclaredField(name);
                                field.setAccessible(true);
                                switch (field.getType().getName()) {
                                    case "java.lang.String":
                                        field.set(ob, value);
                                        break;
                                    case "boolean":
                                        field.setBoolean(ob, Boolean.valueOf(value));
                                        break;
                                    case "int":

                                        field.setInt(ob, Integer.valueOf(value));
                                        break;
                                    case "float":
                                        field.setFloat(ob, Float.valueOf(value));
                                        break;
                                    case "double":
                                        field.setDouble(ob, Double.valueOf(value));
                                        break;
                                }
                            }
                        }
                    }

                    Map<String, String> map = new HashMap<>();
                    for (String lan : page.getLocaleList()) {
                        int colIndex = page.getColumnIndexIgnoreCase(lan);
                        if (array.length > colIndex) {
                            map.put(lan, array[colIndex]);
                        }
                    }
                    Field field = c.getDeclaredField("values");
                    field.setAccessible(true);
                    field.set(ob, map);
                    words.add((Word) ob);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
            }
            page.setWord(words);
            return page;
        }

        return null;
    }


    /**
     * 一个Word对于txt中的一行,且各列中采用的"\t"作为分隔符，为不影响txt格式 会将字符中的一些特殊字符替换
     * 字符中的"\n"会被替换成"\\n", "\n"会被替换成"    ";
     * 其中"\n"换行符 被替换成 "\\n"在Android 中是可以换行的
     **/
    @Override
    public void write(String path, Page page, PageConfig config) {
        if (page == null) return;

        Map<String, Integer> columnName;

        if (config != null && config.getOutColumnName() != null) {
            columnName = new HashMap<>();
            String[] list = config.getOutColumnName();
            for (int i = 0; i < list.length; i++) {
                columnName.put(list[i], i);
            }
        } else {
            columnName = page.getColumnNameMap();
        }
        if (columnName == null) {
            throw new NumberFormatException("Column name is null");
        }
        int maxCol = 0;

        for (int i : columnName.values()) {
            if (i > maxCol) {
                maxCol = i;
            }
        }
        ++maxCol;

        String[] titleRow = new String[maxCol];
        columnName.forEach((name, index) -> {
            if (hasSpecialChar(name)) {
                throw new NumberFormatException("Txt title Row can not contain \\n or \\t :" + name);
            }
            titleRow[index] = name;
        });

        List<String[]> lines = new ArrayList<>();
        lines.add(titleRow);
        List<Word> words = page.getWord();

        if (words != null) {
            for (int i = 0; i < words.size(); i++) {
                Word word = words.get(i);
                if (word == null) continue;
                String[] row = new String[maxCol];
                try {
                    List<String> property = page.getPropertyList();
                    Class c = Class.forName("com.xh.translate.bean.Word");

                    if (property != null) {
                        for (String col : property) {
                            int index = page.getColumnIndexIgnoreCase(columnName, col);
                            if (index >= 0) {
                                Field field = c.getDeclaredField(col);
                                field.setAccessible(true);
                                switch (field.getType().getName()) {
                                    case "java.lang.String":
                                        row[index] = replaceSpecialChar((String) field.get(word));
                                        break;
                                    case "boolean":
                                        if ("translatable".equals(field.getName())) {
                                            if (!field.getBoolean(word)) {
                                                row[index] = replaceSpecialChar(String.valueOf(field.getBoolean(word)));
                                            }
                                        } else {
                                            row[index] = replaceSpecialChar(String.valueOf(field.getBoolean(word)));
                                        }
                                        break;
                                    case "int":
                                        row[index] = replaceSpecialChar(String.valueOf(field.getInt(word)));
                                        break;
                                    case "float":
                                        row[index] = replaceSpecialChar(String.valueOf(field.getFloat(word)));
                                        break;
                                    case "double":
                                        row[index] = replaceSpecialChar(String.valueOf(field.getDouble(word)));
                                        break;
                                }
                            }
                        }
                    }

                    List<String> locales = page.getLocaleList();
                    if (locales != null) {
                        for (String col : locales) {
                            int index = page.getColumnIndexIgnoreCase(columnName, col);
                            if (index >= 0) {
                                if (word.getValues() != null) {
                                    row[index] = replaceSpecialChar(word.getValues().get(col));
                                }
                            }
                        }
                    }

                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (NoSuchFieldException e) {
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                lines.add(row);
            }

            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(path));

                for (String[] array : lines) {
                    StringBuilder buff = new StringBuilder();
                    if (null != array) {
                        for (String cell : array) {
                            buff.append(cell == null ? "" + TAB : cell + TAB);
                        }
                    }
                    writer.write(buff.toString() + "\n");
                }
                writer.close();
                System.out.println("File write over: " + path);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean hasSpecialChar(String str) {
        if (str != null) {
            return str.contains(TAB) || str.contains("\n");
        }
        return false;
    }

    private String replaceSpecialChar(String str) {
        if (str == null) return null;

        str = str.replace("\n", "\\n");
        str = str.replace(TAB, "    ");
        return str;
    }

}
