package com.dsdl.eidea.devs.i18n;


import com.alibaba.druid.sql.visitor.functions.Locate;
import com.dsdl.eidea.util.LocaleHelper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 刘大磊 on 2017/1/12 16:43.
 */
@Slf4j
public class TranslateHelper {
    static Gson gson=new Gson();
    private static String APP_ID = "20170112000035735";
    private static String SECURITY_KEY = "G7eo1pj7WAfhN1vpNKzA";
    private static final TransApi api = new TransApi(APP_ID, SECURITY_KEY);
    /**
     * 百度是个很不规范的公司，使用的语言代码跟国际语言代码不一样，需要做一下迎神
     */
    private static Map<String, String> languageMapBaiduLanguage = new HashMap<>();

    static {
        languageMapBaiduLanguage.put("ar", "ara");
        languageMapBaiduLanguage.put("jp", "ja");
        languageMapBaiduLanguage.put("DE", "de");
        languageMapBaiduLanguage.put("es", "spa");
        languageMapBaiduLanguage.put("fr", "fra");
        languageMapBaiduLanguage.put("ko", "kor");
        languageMapBaiduLanguage.put("ru", "ru");
        languageMapBaiduLanguage.put("ta", "th");
    }

    private static String getLanguageFromLangToBaidu(String language) {
        String baiduLanguage = languageMapBaiduLanguage.get(language);
        if (baiduLanguage == null) {
            return language;
        }
        return baiduLanguage;
    }

    public static String translate(String message, String from, String to) {
        from = getLanguageFromLangToBaidu(from);
        to = getLanguageFromLangToBaidu(to);
        String resultJson = api.getTransResult(message, LocaleHelper.geLanguageCode(from), LocaleHelper.geLanguageCode(to));
        System.out.println(resultJson);
        TranslateResult translateResult=gson.fromJson(resultJson,TranslateResult.class);
        if(translateResult.getErrorCode()!=null)
        {
            log.error(translateResult.getErrorCode()+translateResult.getErrorMsg()+" "+translateResult.getQuery());
            System.out.println(translateResult.getErrorCode()+translateResult.getErrorMsg()+" "+translateResult.getQuery()+"窗体信息翻译失败");
            return from;
        }

        return translateResult.getTransResult().get(0).getDst();
    }

    public static void main(String[] args) {
        System.out.println(translate("你好", "zh", "en"));
        System.out.println(translate("你好", "zh", "fr"));
    }
    @Getter
    @Setter
    class Result
    {
        private String src;
        private String dst;
    }
    @Getter
    @Setter
    class TranslateResult {
        @SerializedName("error_code")
        private String errorCode;
        @SerializedName("error_msg")
        private String errorMsg;

        private String from;

        private String to;
        private String monLang;
        private String query;
        @SerializedName("trans_result")
        private List<Result> transResult;
    }
}
