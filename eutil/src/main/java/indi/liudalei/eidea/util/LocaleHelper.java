package indi.liudalei.eidea.util;

import java.util.Locale;

/**
 * Created by 刘大磊 on 2017/1/12 11:05.
 */
public class LocaleHelper {
    private static final int POSITION_LANGUAGE = 0;
    private static final int POSITION_COUNTRY = 1;

    public static Locale parseLocale(String localStr) {
        if (StringUtil.isNotEmpty(localStr)) {
            String[] languages = localStr.split("_");
            if (languages != null) {
                if (languages.length == 1) {
                    return new Locale(languages[POSITION_LANGUAGE]);
                } else {
                    return new Locale(languages[POSITION_LANGUAGE], languages[POSITION_COUNTRY]);
                }
            }
        }
        return Locale.SIMPLIFIED_CHINESE;
    }

    public static String geLanguageCode(String localStr) {
        if (StringUtil.isNotEmpty(localStr)) {
            String[] languages = localStr.split("_");
            return languages[POSITION_LANGUAGE];
        }
        return null;
    }
}
