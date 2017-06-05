package cryptography;

        import sun.misc.BASE64Encoder;

        import java.io.UnsupportedEncodingException;
        import java.security.MessageDigest;
        import java.security.NoSuchAlgorithmException;

/**
 * Created by joseph on 2017/6/1.
 */
public class Md5Util {
    public String EncoderByMd5(String str)  {
        //确定计算方法
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            //对字符串进行Md5
            String newstr = new AesUtil().parseByte2HexStr(md5.digest(str.getBytes("utf-8")));
            return newstr;
        } catch (NoSuchAlgorithmException |UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
