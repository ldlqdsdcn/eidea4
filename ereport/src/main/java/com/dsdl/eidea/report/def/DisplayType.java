package com.dsdl.eidea.report.def;

/**
 * Created by admin on 2016/9/7.
 */
public enum DisplayType {
    EXCEL(0,"Excel"),PDF(1,"PDF"),PDFZIP(2,"PDFZIP");

    DisplayType(int key,String desc)
    {
        this.key=key;
        this.desc=desc;
    }
    private int key;
    private String desc;

    public int getKey() {
        return key;
    }

    public String getDesc() {
        return desc;
    }
}
