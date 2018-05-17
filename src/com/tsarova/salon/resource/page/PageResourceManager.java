package com.tsarova.salon.resource.page;

import java.util.ResourceBundle;

public class PageResourceManager {

    private static final PageResourceManager instance = new PageResourceManager();

    private ResourceBundle bundle = ResourceBundle
            .getBundle("prop.page");

    public static PageResourceManager getInstance() {
        return instance;
    }

    public String getValue(String key) {
        return bundle.getString(key);
    }
}
