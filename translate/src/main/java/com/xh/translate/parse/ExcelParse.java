package com.xh.translate.parse;



import com.xh.translate.PageConfig;
import com.xh.translate.bean.Page;
import com.xh.translate.bean.Word;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.apache.poi.ss.usermodel.Row.MissingCellPolicy.RETURN_BLANK_AS_NULL;


public class ExcelParse implements Parse {

    /**
     * 注意事项
     * sheet.getLastRowNum() 返回的是Row下标
     * row.getLastCellNum() 返回的是Cell的长度
     */
    @Override
    public Page read(String path, PageConfig config) {
        try {
            Workbook wb = new XSSFWorkbook(new FileInputStream(path));

            Sheet sheet = wb.getSheetAt(wb.getNumberOfSheets() - 1);
            Row titleRow = sheet.getRow(sheet.getFirstRowNum());
            if (titleRow == null) {
                return null;
            }

            Page page = new Page();
            Map<String, Integer> colName = new HashMap<>();

            for (int i = titleRow.getFirstCellNum(); i < titleRow.getLastCellNum(); i++) {
                String value = getCellStringValueFromRow(titleRow, i);
                if (value != null && !value.trim().equals("")) {
                    if (colName.keySet().contains(value.trim())) {
                        throw new NumberFormatException("Cannot exist the same column name \'" + value.trim() + "\' in Excel: " + path);
                    }
                    colName.put(value.trim(), i);
                }
            }
            page.setColumnName(colName, config);

            if (page.getLocaleList() != null && !page.getLocaleList().isEmpty()) {
                List<Word> words = new ArrayList<>();
                for (int i = sheet.getFirstRowNum() + 1; i <= sheet.getLastRowNum(); i++) {
                    Row row = sheet.getRow(i);
                    if (row == null) continue;
                    try {
                        Class c = Class.forName("com.xh.translate.bean.Word");
                        Object ob = c.newInstance();
                        for (String name : page.getPropertyList()) {
                            int colIndex = page.getColumnIndexIgnoreCase(name);
                            Cell cell = row.getCell(colIndex, RETURN_BLANK_AS_NULL);
                            if (cell != null) {
                                Field field = c.getDeclaredField(name);
                                field.setAccessible(true);
                                switch (field.getType().getName()) {
                                    case "java.lang.String":
                                        field.set(ob, cell.getStringCellValue());
                                        break;
                                    case "boolean":
                                        field.setBoolean(ob, cell.getBooleanCellValue());
                                        break;
                                    case "int":
                                        field.setInt(ob, (int) cell.getNumericCellValue());
                                        break;
                                    case "float":
                                        field.setFloat(ob, (float) cell.getNumericCellValue());
                                        break;
                                    case "double":
                                        field.setDouble(ob, cell.getNumericCellValue());
                                        break;
                                }
                            }
                        }

                        Map<String, String> map = new HashMap<>();
                        for (String lan : page.getLocaleList()) {
                            int colIndex = page.getColumnIndexIgnoreCase(lan);
                            map.put(lan, getCellStringValueFromRow(row, colIndex));
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void write(String path, Page page, PageConfig config) {
        if (page == null) return;

        Workbook wb;
        try {
            FileInputStream in = new FileInputStream(path);
            wb = new XSSFWorkbook(in);
        } catch (IOException e) {
            wb = new XSSFWorkbook();
        }

        Font font = wb.createFont();
        Font titleFont = wb.createFont();
        font.setFontHeightInPoints((short) 12); //default 11
        titleFont.setFontHeightInPoints((short) 12);
        titleFont.setColor(IndexedColors.RED.getIndex());

        CellStyle contentCellStyle = wb.createCellStyle();
        contentCellStyle.setAlignment(HorizontalAlignment.LEFT);
        contentCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        contentCellStyle.setFont(font);

        CellStyle titleCellStyle = wb.createCellStyle();
        titleCellStyle.setAlignment(HorizontalAlignment.LEFT);
        titleCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        titleCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        titleCellStyle.setFillForegroundColor((short) 37); //实际设置的是背景色,必须设置充填模式才能生效
        titleCellStyle.setFont(titleFont);

        Sheet sheet = wb.createSheet();

        sheet.setDefaultRowHeight((short) 350); //default 300
        sheet.setDefaultColumnWidth(20);       //default 8

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

        Row titleRow = sheet.createRow(0);
        columnName.forEach((name, index) -> {
            Cell cell = titleRow.createCell(index);
            cell.setCellStyle(titleCellStyle);
            cell.setCellValue(name);
        });

        List<Word> words = page.getWord();
        if (words != null) {
            for (int i = 0; i < words.size(); i++) {
                Word word = words.get(i);
                if (word == null) continue;
                Row row = sheet.createRow(i + 1);
                try {
                    List<String> property = page.getPropertyList();
                    Class c = Class.forName("com.xh.translate.bean.Word");

                    if (property != null) {
                        for (String col : property) {
                            int index = page.getColumnIndexIgnoreCase(columnName, col);
                            if (index >= 0) {
                                Field field = c.getDeclaredField(col);
                                field.setAccessible(true);
                                Cell cell = row.createCell(index);
                                cell.setCellStyle(contentCellStyle);
                                switch (field.getType().getName()) {
                                    case "java.lang.String":
                                        cell.setCellValue((String) field.get(word));
                                        break;
                                    case "boolean":
                                        if ("translatable".equals(field.getName())) {
                                            if (!field.getBoolean(word)) {
                                                cell.setCellValue(field.getBoolean(word));
                                            }
                                        } else {
                                            cell.setCellValue(field.getBoolean(word));
                                        }
                                        break;
                                    case "int":
                                        cell.setCellValue(field.getInt(word));
                                        break;
                                    case "float":
                                        cell.setCellValue(field.getFloat(word));
                                        break;
                                    case "double":
                                        cell.setCellValue(field.getDouble(word));
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
                                Cell cell = row.createCell(index);
                                cell.setCellStyle(contentCellStyle);
                                if (word.getValues() != null) {
                                    cell.setCellValue(word.getValues().get(col));
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
            }

            try {
                FileOutputStream out = new FileOutputStream(path);
                wb.write(out);
                out.close();
                System.out.println("File write over: " + path);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String getCellStringValueFromRow(Row row, int index) {
        if (row == null) return null;
        Cell cell = row.getCell(index);

        if (cell != null) {
            return cell.getStringCellValue();
        }
        return null;
    }
}
