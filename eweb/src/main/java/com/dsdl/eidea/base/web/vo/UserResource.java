package com.dsdl.eidea.base.web.vo;

import com.dsdl.eidea.core.i18n.DbResourceBundle;

import java.util.Locale;

/**
 * Created by 刘大磊 on 2017/1/7 14:08.
 */
public class UserResource implements java.io.Serializable {
    private DbResourceBundle bundle = null;
    private Locale locale;

    /**
     *
     */
    public UserResource(Locale locale,DbResourceBundle dbResourceBundle) {
        this.locale = locale;
        bundle = dbResourceBundle;
    }

    public String getMessage(String key) {
        return bundle.getMessage(key);
    }
    public String getMessage(String key, Object... value)
    {
        return bundle.getMessage(key,value);
    }
    public String getLabel(String key) {
        return bundle.getLabel(key);
    }
    public String getLabel(String key, Object... value)
    {
        return bundle.getLabel(key,value);
    }

    public Locale getLocaleObj() {
        return locale;
    }

    public String getLocale() {
        return locale.toString();
    }
}
