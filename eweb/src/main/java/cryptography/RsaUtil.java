package cryptography;

/**
 * Created by joseph on 2017/4/18.
 */

import jdk.internal.dynalink.beans.StaticClass;
import org.springframework.core.io.ClassPathResource;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Properties;

/**
 * 公钥和私钥已经保存在rsa.properties文件中
 * RsaUtil主要用来从rsa.properties中读取私钥并还原
 * 利用还原出来的私钥还原aes中已经用rsa公钥加密后的密钥
 */

public class RsaUtil {
    public static final String KEY_ALGORITHM = "RSA";
    public static final String rsaPropertiesLocation = "rsa.properties";

    /**
     * 利用读取的私钥对在前端用rsa加密过的密钥进行解密
     * Cipher指定了加解密方式为RSA，模式为ECB，填充方式为PKCS1Padding这也是默认的RSA加解密方式
     * 然后从rsaPropertiesLocation中读取私钥，利用私钥规范还原私钥
     * 再利用还原出来的私钥进行rsa解密
     * @param encodedText
     * @return
     */
    public String rsaDecode(String encodedText) {
        try {
            Cipher rsaCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            byte[] bytesPrivatekey = readKey("stringBase64Privatekey", rsaPropertiesLocation);
            PrivateKey privateKey = restorePrivateKey(bytesPrivatekey);
            rsaCipher.init(Cipher.DECRYPT_MODE,privateKey);
            byte [] byte_content = new sun.misc.BASE64Decoder().decodeBuffer(encodedText);
            String decryptedText =new String(rsaCipher.doFinal(byte_content));
            return decryptedText;
        } catch (NoSuchAlgorithmException
                |NoSuchPaddingException
                |InvalidKeyException
                |BadPaddingException
                |IllegalBlockSizeException
                |IOException e) {
            throw fail(e);
        }
    }
    /**
     * 读取密钥,keyname为stringBase64Privatekey
     * @param keyName
     * @param filename
     * @return
     */
    private byte[] readKey(String keyName , String filename){
        Properties pps = new Properties();
        try {

            InputStream in = new BufferedInputStream(new ClassPathResource(filename).getInputStream());
            pps.load(in);
            String stringBase64key = pps.getProperty(keyName);
            byte[] byteKey = new sun.misc.BASE64Decoder().decodeBuffer(stringBase64key);
            return byteKey;
        } catch (IOException e) {
            throw fail(e);
        }
    }

    /**
     * 还原私钥，PKCS8EncodedKeySpec 用于构建私钥的规范
     *
     * @param keyBytes
     * @return
     */
    private PrivateKey restorePrivateKey(byte[] keyBytes) {
        try {
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyBytes);
            KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM);
            PrivateKey restoredPrivatekey = factory.generatePrivate(pkcs8EncodedKeySpec);
            return restoredPrivatekey;
        } catch (NoSuchAlgorithmException
                |InvalidKeySpecException e) {
            throw fail(e);
        }
    }
    private IllegalStateException fail(Exception e) {
        return new IllegalStateException(e);
    }
}

