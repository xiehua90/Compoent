package com.xh.translate;


import java.util.Arrays;
import java.util.List;

public class PageConfig {
    private String[] inLocale; //输入Locales 控制
    private String[] outColumnName; //输出列控制
    private String[] outLocalesDirName; //输出xml的IOS语言缩写,如 zh,es,de,zh-rCN等,程序会以此字符和values拼接作为xml父文件夹
    private String defaultLocalesColName = "en";
    private boolean xmlWriteReplace = false; //是否替换string.xml原有字段, true则替换, false则不替换
    private String[] duplicateLocale; //设置要输出重复的列
    private String referLocale = "en"; //设置要对比列


    public String[] getOutColumnName() {
        return outColumnName;
    }

    public void setOutColumnName(String... outColumnName) {
        this.outColumnName = outColumnName;

        if (outColumnName != null) {
            List<String> list = Arrays.asList(outColumnName);

            for (String s : outColumnName) {
                if (s != null && !s.trim().equals("")) {
                    if (list.indexOf(s) != list.lastIndexOf(s)) {
                        throw new NumberFormatException("Cannot exist the same column name: " + s);
                    }
                }
            }
        }
    }

    public String[] getInLocale() {
        return inLocale;
    }

    public void setInLocale(String... inLocale) {
        this.inLocale = inLocale;
    }

    public String getDefaultLocalesColName() {
        return defaultLocalesColName;
    }

    public void setDefaultLocalesColName(String defaultLocalesColName) {
        if (defaultLocalesColName == null || defaultLocalesColName.trim().equals("")) return;
        this.defaultLocalesColName = defaultLocalesColName;
    }

    public String[] getOutLocalesDirName() {
        return outLocalesDirName;
    }

    public void setOutLocalesDirName(String... outLocalesDirName) {
        this.outLocalesDirName = outLocalesDirName;
    }

    public boolean isXmlWriteReplace() {
        return xmlWriteReplace;
    }

    public void setXmlWriteReplace(boolean xmlWriteReplace) {
        this.xmlWriteReplace = xmlWriteReplace;
    }

    public String[] getDuplicateLocale() {
        return duplicateLocale;
    }

    public void setDuplicateLocale(String... duplicateLocale) {
        this.duplicateLocale = duplicateLocale;
    }

    public String getReferLocale() {
        return referLocale;
    }

    public void setReferLocale(String referLocale) {
        if (referLocale == null || referLocale.trim().equals("")) return;
        this.referLocale = referLocale;
    }
}
