package com.dsdl.eidea.util;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 刘大磊 on 2017/8/31 11:02.
 */
public class RestHelper {

    public RestHelper(Map<String, String> headers) {
        this.headers = headers;
    }

    public RestHelper() {
    }

    private Map<String, String> headers = new HashMap<>();

    public void addHeader(String key, String value) {
        this.headers.put(key, value);
    }

    public ResponseEntity postBody(String url, String body, Map<String, String> headerMap) {
        HttpStatus responseCode = null;
        StringBuilder result = new StringBuilder();
        try {
            HttpURLConnection httpURLConnection = getHttpURLConnection(url);
            httpURLConnection.setRequestMethod("POST");// 提交模式
            // conn.setConnectTimeout(10000);//连接超时 单位毫秒
            // conn.setReadTimeout(2000);//读取超时 单位毫秒
            // 发送POST请求必须设置如下两行
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            if (headerMap != null) {
                for (String key : headerMap.keySet()) {
                    httpURLConnection.setRequestProperty(key, headerMap.get(key));
                }
            }

            // 获取URLConnection对象对应的输出流
            PrintWriter printWriter = new PrintWriter(httpURLConnection.getOutputStream());
            // 发送请求参数

            printWriter.write(body);//post的参数 xx=xx&yy=yy
            // flush输出流的缓冲
            printWriter.flush();

            BufferedReader in = null;
            //开始获取数据
            if (200 <= httpURLConnection.getResponseCode() && httpURLConnection.getResponseCode() <= 299) {
                in = new BufferedReader(new InputStreamReader((httpURLConnection.getInputStream()), "UTF-8"));
                responseCode = HttpStatus.OK;
            } else {
                responseCode = HttpStatus.ERROR;
                in = new BufferedReader(new InputStreamReader((httpURLConnection.getErrorStream()), "UTF-8"));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity(responseCode, result.toString());
    }

    public ResponseEntity postBody(String url, String body) {
        return postBody(url, body, null);
    }

    public ResponseEntity post(String url, Map<String, Object> param, Map<String, String> headerMap) {
        StringBuilder paramStr = new StringBuilder();
        boolean isFirst = true;
        for (String key : param.keySet()) {
            if (isFirst) {
                isFirst = false;
            } else {
                paramStr.append("&");
            }
            paramStr.append(key).append("=").append(param.get(key));
        }
        return postBody(url, paramStr.toString(), headerMap);
    }

    /**
     * 发送传统数据
     *
     * @param url   请求http地址
     * @param param
     * @return
     */
    public ResponseEntity post(String url, Map<String, Object> param) {
        return this.post(url, param, null);
    }

    public ResponseEntity get(String url, Map<String, Object> param, Map<String, String> headerMap) {
        StringBuilder result = new StringBuilder();
        BufferedReader in = null;
        HttpStatus responseCode = null;
        try {
            StringBuilder urlNameString = new StringBuilder(url);
            boolean isFirst = true;
            for (String key : param.keySet()) {
                if (isFirst) {
                    urlNameString.append("?");
                    isFirst = false;
                } else {
                    urlNameString.append("&");
                }
                urlNameString.append(key).append("=").append(encode(String.valueOf(param.get(key))));
            }
            // 打开和URL之间的连接
            HttpURLConnection connection = getHttpURLConnection(urlNameString.toString());

            if (headerMap != null) {
                for (String key : headerMap.keySet()) {
                    connection.setRequestProperty(key, headerMap.get(key));
                }
            }
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            //Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            /*for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }*/
            if (200 <= connection.getResponseCode() && connection.getResponseCode() <= 299) {
                responseCode = HttpStatus.OK;

                in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            } else {
                responseCode = HttpStatus.ERROR;
                in = new BufferedReader(new InputStreamReader(connection.getErrorStream(), "UTF-8"));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (IOException e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return new ResponseEntity(responseCode, result.toString());
    }

    public ResponseEntity get(String url, Map<String, Object> param) {
        return this.get(url, param, null);
    }


    public enum HttpStatus {
        OK, ERROR
    }

    public static class ResponseEntity {
        ResponseEntity(HttpStatus status, String body) {
            this.status = status;
            this.body = body;
        }

        private HttpStatus status;
        private String body;

        public HttpStatus getStatus() {
            return status;
        }

        public void setStatus(HttpStatus status) {
            this.status = status;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }
    }

    /**
     * 对输入的字符串进行URL编码, 即转换为%20这种形式
     *
     * @param input 原文
     * @return URL编码. 如果编码失败, 则返回原文
     */
    public static String encode(String input) {
        if (input == null) {
            return "";
        }

        try {
            return URLEncoder.encode(input, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return input;
    }

    private HttpURLConnection getHttpURLConnection(String url) {
        HttpURLConnection connection = null;
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            connection = (HttpURLConnection) realUrl.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        initHttps(connection);
        // 设置通用的请求属性
        for (String key : headers.keySet()) {
            connection.setRequestProperty(key, headers.get(key));
        }
        connection.setRequestProperty("accept", "*/*");
        connection.setRequestProperty("connection", "Keep-Alive");
        connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        return connection;
    }

    private void initHttps(HttpURLConnection httpURLConnection) {
        if (httpURLConnection instanceof HttpsURLConnection) {
            // 设置SSLContext
            SSLContext sslcontext = null;
            try {
                sslcontext = SSLContext.getInstance("TLS");
                sslcontext.init(null, new TrustManager[]{myX509TrustManager}, null);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (KeyManagementException e) {
                e.printStackTrace();
            }
            ((HttpsURLConnection) httpURLConnection).setSSLSocketFactory(sslcontext.getSocketFactory());
        }
    }

    private static TrustManager myX509TrustManager = new X509TrustManager() {

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }
    };
}
