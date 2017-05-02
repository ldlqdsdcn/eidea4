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
 */

public class RsaUtil {
    public static final String KEY_ALGORITHM = "RSA";
    public static final String rsaPropertiesLocation = "rsa.properties";
    public String rsaEncode( byte[] plainText) {
        try {
            Cipher rsaCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            byte[] bytesPublickey = readKey("stringBase64Publickey", rsaPropertiesLocation);
            PublicKey publicKey = restorePublicKey(bytesPublickey);
            rsaCipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] ciphertext = rsaCipher.doFinal(plainText);
            //String rsa_encode = base64(ciphertext);
            String rsa_encode = new sun.misc.BASE64Encoder().encodeBuffer(ciphertext);
            return rsa_encode;
        } catch (NoSuchAlgorithmException
                |NoSuchPaddingException
                |BadPaddingException
                |IllegalBlockSizeException
                |InvalidKeyException e) {
            throw fail(e);
        }
    }
    public String rsaDecode(String encodedText) {
        try {
            Cipher rsaCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            byte[] bytesPrivatekey = readKey("stringBase64Privatekey", rsaPropertiesLocation);
            PrivateKey privateKey = restorePrivateKey(bytesPrivatekey);
            rsaCipher.init(Cipher.DECRYPT_MODE,privateKey);
            //byte [] byte_content = base64(encodedText);
            //byte [] byte_content = encodedText.getBytes();
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
     * 读取密钥,keyname为stringBase64Publickey或是stringBase64Privatekey
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
            //byte[] byteKey = base64(stringBase64key);
            byte[] byteKey = new sun.misc.BASE64Decoder().decodeBuffer(stringBase64key);
            return byteKey;
        } catch (IOException e) {
            throw fail(e);
        }
    }

    /**
     * 还原公钥，X509EncodedKeySpec 用于构建公钥的规范
     *
     * @param keyBytes
     * @return
     */
    private  PublicKey restorePublicKey(byte[] keyBytes) {
        try {
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
            KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM);
            PublicKey restoredPublickey = factory.generatePublic(x509EncodedKeySpec);
            return restoredPublickey;
        } catch (NoSuchAlgorithmException
                |InvalidKeySpecException e) {
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

    public static String base64(byte[] bytes){
        String base64str = Base64.getEncoder().encodeToString(bytes);
        return base64str;
    }

    public static byte[] base64(String str){
        byte[] base64bytes = Base64.getDecoder().decode(str);
        return base64bytes;
    }
    private IllegalStateException fail(Exception e) {
        return new IllegalStateException(e);
    }
}

