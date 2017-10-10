package cryptography;


import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.*;


/**
 * Created by joseph on 2017/4/18.
 * AesUtil主要解决的就是对传递的密文进行aes解密
 * 解密后的用utf8字符集
 */
public class AesUtil {

    private final Cipher cipher;

    /**
     * 初始化Cipher，指定了加解密模式为CBC，填充方式为PKCS7Padding，provider为BC
     * 由于java自带的aes加解密中的填充方式中没有PKCS7Padding
     * 所以添加了BouncyCastle包
     */

    public AesUtil(){
        try {
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            cipher = Cipher.getInstance("AES/CBC/PKCS7Padding" ,"BC");
        } catch (NoSuchAlgorithmException
                |NoSuchPaddingException
                |NoSuchProviderException e) {
            throw fail(e);
        }
    }

    /**
     * 利用密钥key和密钥偏移量iv对密文ciphertext进行解密
     * 先将iv和key转换为我们需要的byte[]加解密
     * 返回值为utf8的字符串
     * @param key
     * @param iv
     * @param ciphertext
     * @return
     */
    public String aesDecode(String key , String iv, String ciphertext){
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
    /**将二进制转换成16进制
     * @param buf
     * @return
     */
    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }
    private IllegalStateException fail(Exception e) {
        return new IllegalStateException(e);
    }
}
