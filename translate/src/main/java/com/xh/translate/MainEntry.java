package com.xh.translate;


public class MainEntry {

    public static void main(String[] args) {
        final String resPath = "C:/Users/A18060/Desktop/zxing/app/src/main/res";
        final String desktopPath = "C:/Users/A18060/Desktop/";

        String srcPath = resPath;
        String desPath = desktopPath + "android.xlsx";

        TranslateHelper.convert(srcPath, desPath, null); //string.xml 转 excel，是写到Excel最后一个sheet
        TranslateHelper.convert(desPath, desktopPath + "/res", null);  //excel 转 string.xml

        PageConfig config = new PageConfig();
        config.setInLocale("en", "zh", "zh-rCN", "fr", "de"); //设置读入的Locale
        TranslateHelper.convert(srcPath, desPath, config);


        config.setOutColumnName("key", "en", "zh", "zh-rCN");//设置输出的Locale
        TranslateHelper.convert(srcPath, desPath, config);

        String refer = desPath;

        //查询原来项目中refer已有的翻译字段,并将查找到的字段写到desPath文件里面
        TranslateHelper.searchOldTranslate(srcPath, refer, desktopPath + "refer.xlsx", config);

//        PageConfig config = new PageConfig();
//
//        //values文件下的strings.xml导出到Excel表格的last sheet;
//        TranslateHelper.convert(srcPath, desPath, config);
//
//        config.setInLocale("en", "zh", "zh-rCN", "fr", "de"); //设置读入的Locale
//        TranslateHelper.convert(srcPath, desPath, config);
//
//        config.setOutColumnName("key", "en", "zh", "zh-rCN");//设置输出的Locale
//        TranslateHelper.convert(srcPath, desPath, config);
//
//        //Excel first sheet写入values文件下的strings.xml
//        srcPath = desktopPath + "android.xlsx";
//        desPath = desktopPath + "/res";
//
//        config.setInLocale(null);
//        config.setOutColumnName(null);
//        TranslateHelper.convert(srcPath, desPath, config);
//
//        config.setInLocale("en", "zh", "zh-rCN", "fr", "de"); //设置读入的Locale
//        TranslateHelper.convert(srcPath, desPath, config);
//
//        config.setOutColumnName("en", "zh", "zh-rCN");//设置输出的Locale
//        TranslateHelper.convert(srcPath, desPath, config);
    }




}
