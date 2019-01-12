package com.xh.translate.parse;


import com.xh.translate.PageConfig;
import com.xh.translate.bean.Page;

public interface Parse {
    Page read(String path, PageConfig config);

    void write(String path, Page page, PageConfig config);
}
