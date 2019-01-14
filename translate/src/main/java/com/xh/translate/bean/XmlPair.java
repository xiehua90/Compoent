package com.xh.translate.bean;

public class XmlPair {
    private String key;
    private String value;
    private boolean translatable = true;

    public XmlPair() {
    }

    public XmlPair(String key, String value, boolean translatable) {
        this.key = key;
        this.value = value;
        this.translatable = translatable;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean isTranslatable() {
        return translatable;
    }

    public void setTranslatable(boolean translatable) {
        this.translatable = translatable;
    }


    public boolean isValidXmlPair() {
        return key != null
                && !key.trim().equals("")
                && value != null
                && !value.trim().equals("");
    }

    @Override
    public String toString() {
        String property = translatable ? "" : " translatable=\"false\" ";
        return String.format("<string name=\"%s\"%s>%s</string>", key, property, value);
    }
}
