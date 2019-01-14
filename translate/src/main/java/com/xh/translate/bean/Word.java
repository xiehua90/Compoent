package com.xh.translate.bean;


import java.util.HashMap;
import java.util.Map;

public class Word {
    private String key;
    private boolean translatable = true;
    private Map<String, String> values;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Map<String, String> getValues() {
        return values;
    }

    public void setValues(Map<String, String> values) {
        this.values = values;
    }

    public boolean isTranslatable() {
        return translatable;
    }

    public void setTranslatable(boolean translatable) {
        this.translatable = translatable;
    }

    public void appendPair(XmlPair pair, String locales, boolean isDefaultLocales) {
        if (pair == null || locales == null) return;
        if (this.key == null || this.key.trim().equals("")) {
            if (!pair.getKey().trim().equals("")) {
                this.key = pair.getKey();
            }
        }

        if (isDefaultLocales) {
            this.translatable = pair.isTranslatable();
        }

        if (values == null) {
            values = new HashMap<>();
        }

        values.put(locales, pair.getValue());
    }

    public XmlPair getXmlPair(String locales) {
        if (locales == null || values == null) return null;
        return new XmlPair(key, values.get(locales), translatable);
    }

}
