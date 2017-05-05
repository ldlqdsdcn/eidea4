package cryptography;

/**
 * Created by joseph on 2017/4/18.
 */

import org.springframework.core.io.ClassPathResource;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.security.*;
import java.security.cert.CertificateException;

/**
 * 公钥和私钥已经保存在dingsheng.keystore中
 * .keystore文件是使用keytool生成的
 * RsaUtil主要用来从.keystore中读取私钥
 * 利用私钥还原aes中已经用rsa公钥加密后的密钥
 */

public class RsaUtil {
    public static final String keystoreLocation = "dingsheng.keystore";

    /**
     * 利用读取的私钥对在前端用rsa加密过的密钥进行解密
     * Cipher指定了加解密方式为RSA，模式为ECB，填充方式为PKCS1Padding这也是默认的RSA加解密方式
     * 然后从keystore文件中读取私钥
     * 进行rsa解密
     * @param encodedText
     * @return
     */
    public String rsaDecode(String encodedText) {

        try {
            Cipher rsaCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            PrivateKey privateKey = readKey(keystoreLocation);
            rsaCipher.init(Cipher.DECRYPT_MODE,privateKey);
            byte [] byte_content = new sun.misc.BASE64Decoder().decodeBuffer(encodedText);
            byte[] decryptedText = rsaCipher.doFinal(byte_content);
            String decryptedstr = new String(decryptedText);
            return decryptedstr;
        } catch (IOException
                |NoSuchAlgorithmException
                |InvalidKeyException
                |NoSuchPaddingException
                |BadPaddingException
                |IllegalBlockSizeException e) {
            throw fail(e);
        }
    }
    /**
     * 从keystore中读取密钥
     * 对于一个keystore指定了alias和password才能正确的读取撕咬
     * 对于公钥来说有alias就可以正确的读取公钥（此处不涉及读取公钥）
     * @param filename
     * @return
     */
    private PrivateKey readKey( String filename){
            String password = "abcdefg";
            String alias = "dingshengkey";
        try {
            InputStream in = new BufferedInputStream(new ClassPathResource(filename).getInputStream());
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(in,password.toCharArray());
            in.close();
            PrivateKey privateKey = (PrivateKey) keyStore.getKey(alias,password.toCharArray());
            return privateKey;
        } catch (IOException
                |CertificateException
                |NoSuchAlgorithmException
                |UnrecoverableKeyException
                |KeyStoreException e) {
            throw  fail(e);
        }
    }
    private IllegalStateException fail(Exception e) {
        return new IllegalStateException(e);
    }
}

