package com.dsdl.eidea.devs.i18n;


import com.alibaba.druid.sql.visitor.functions.Locate;
import com.dsdl.eidea.util.LocaleHelper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * Created by 刘大磊 on 2017/1/12 16:43.
 */
public class TranslateHelper {
    private static String APP_ID="20170112000035735";
    private static String SECURITY_KEY="G7eo1pj7WAfhN1vpNKzA";
    private static final TransApi api = new TransApi(APP_ID, SECURITY_KEY);
    public static String translate(String message,String from,String to)
    {

        String resultJson=api.getTransResult(message, LocaleHelper.geLanguageCode(from),LocaleHelper.geLanguageCode(to));
        System.out.println(resultJson);
        JsonParser parser = new JsonParser();
        JsonObject jsonObj = (JsonObject) parser.parse(resultJson);

        return jsonObj.getAsJsonArray("trans_result").get(0).getAsJsonObject().get("dst").getAsString();
    }
    public static void main(String[] args)
    {
        System.out.println(translate("窗体信息","zh","en"));
    }
}
