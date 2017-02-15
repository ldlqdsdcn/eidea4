package com.dsdl.eidea.devs.def;

/**
 * Created by admin on 2016/8/30.
 */
public enum  DecoratorType {
    DATE(1,"com.delmar.core.web.displaytag.decorator.DateDecorator"),
    DATETIME(2,"com.delmar.core.web.displaytag.decorator.DateTimeHelper"),
    USER(3,"com.delmar.base.web.displaytag.decorator.UserDecorator"),
    ORG(4,"com.delmar.base.web.displaytag.decorator.OrgDecorator"),
    CLIENT(5,"com.delmar.system.web.displaytag.decorator.ClientDecorator");
    DecoratorType(int key,String className)
    {
        this.key=key;
        this.className=className;
    }
    private final int key;
    private final String className;

    public int getKey() {
        return key;
    }

    public String getClassName() {
        return className;
    }
}
