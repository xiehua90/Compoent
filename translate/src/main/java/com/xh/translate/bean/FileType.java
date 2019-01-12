package com.xh.translate.bean;


public enum FileType {
    EXCEL("xlsx"),
    TXT("txt"),
    DIR(null),
    UNKNOWN(""),;


    private String suffix;

    FileType(String suffix) {
        this.suffix = suffix;
    }


    public static FileType newInstance(String path) {
        if (path == null || path.trim().equals("")) return UNKNOWN;

        int index = path.lastIndexOf(".");
        if (index > 0) {
            String su = path.substring(index + 1);

            if (su.equalsIgnoreCase(EXCEL.suffix)) {
                return EXCEL;
            } else if (su.equalsIgnoreCase(TXT.suffix)) {
                return TXT;
            }
            return UNKNOWN;
        } else {
            return DIR;
        }

    }
}
