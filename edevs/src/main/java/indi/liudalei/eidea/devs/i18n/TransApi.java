package indi.liudalei.eidea.devs.i18n;

import indi.liudalei.eidea.util.RestHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 刘大磊 on 2017/1/12 16:55.
 */
public class TransApi {
    private static final String TRANS_API_HOST = "http://api.fanyi.baidu.com/api/trans/vip/translate";
    private String appid;
    private String securityKey;
    private RestHelper restHelper=new RestHelper();
    public TransApi(String appid, String securityKey) {
        this.appid = appid;
        this.securityKey = securityKey;
    }

    public String getTransResult(String query, String from, String to) {
        Map<String, Object> params = buildParams(query, from, to);
        RestHelper.ResponseEntity responseBody=restHelper.get(TRANS_API_HOST, params);
        return responseBody.getBody();
    }

    private Map<String, Object> buildParams(String query, String from, String to) {


        Map<String, Object> params = new HashMap<>();
        params.put("q", query);
        params.put("from", from);
        params.put("to", to);

        params.put("appid", appid);

        // 随机数
        String salt = String.valueOf(System.currentTimeMillis());
        params.put("salt", salt);

        // 签名
        String src = appid + query + salt + securityKey; // 加密前的原文
        params.put("sign", MD5.md5(src));

        return params;
    }
}
