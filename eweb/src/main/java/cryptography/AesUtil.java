package cryptography;


import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;


/**
 * Created by joseph on 2017/4/18.
 * AesUtil包主要解决的就是对传递的密文进行aes机密
 * 解密后的用utf8字符集
 */
public class AesUtil {

    private final Cipher cipher;
    //初始化
    public AesUtil(){

        try {
            cipher = Cipher.getInstance("AES/CBC/PKCS7Padding" ,"BC");
        } catch (NoSuchAlgorithmException
                |NoSuchPaddingException
                |NoSuchProviderException e) {
            throw fail(e);
        }
    }

    public String aesDecode(String key , String iv, String ciphertext){
        //先将iv和key转换为我们需要的byte[]，我们用的是16进制数组加解密
        byte[] keybytes = parseHexStr2Byte(key);
        byte[] ivbytes = parseHexStr2Byte(iv);
        SecretKey secretKey = new SecretKeySpec(keybytes,"AES");
        try {
            cipher.init(Cipher.DECRYPT_MODE , secretKey , new IvParameterSpec(ivbytes));
            byte [] byte_content = new sun.misc.BASE64Decoder().decodeBuffer(ciphertext);
            byte [] byte_decode=cipher.doFinal(byte_content);
            String AES_decode=new String(byte_decode,"Utf8");
            return AES_decode;

        } catch (InvalidKeyException
                |InvalidAlgorithmParameterException
                |BadPaddingException
                |IllegalBlockSizeException
                |IOException e) {
            throw fail(e);
        }
    }

    /**将16进制转换为二进制
     * @param hexStr
     * @return
     */
    public static byte[] parseHexStr2Byte(String hexStr) {
        if (hexStr.length() < 1)
            return null;
        byte[] result = new byte[hexStr.length()/2];
        for (int i = 0;i< hexStr.length()/2; i++) {
            int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);
            int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);
            result[i] = (byte) (high * 16 + low);
        }
        return result;
    }
    private IllegalStateException fail(Exception e) {
        return new IllegalStateException(e);
    }
}
