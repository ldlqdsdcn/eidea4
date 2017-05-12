/**
 * Created by joseph on 2017/4/17.
 * js只负责aes加密和对密钥的aes加密
 * java端负责对aes的解密和rsa的解密
 * publickey是从.keystore文件中读取的并通过BASE64Encoder转化为字符串格式的
 */
var publickey ="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDOka9i0uG0uf+fnipsxi3+BCBd2CUfkNHSAn6pzCENx/a2xCxnw6Xcb4Y45GKpyktz0yhwBE4Z7kIFHvBFNIHIwFZCKPPlyl9u7VI+UK5/o/sQO/u1d6ktn1L4h41YdrlX78bTiI+am4GSYRgC0ylEDANJT1q13yXvG3/GmktFPwIDAQAB";
//初始化
var AesAndRsaUtil = function(iv, key) {
    this.iv = iv;
    this.key = key;
};
//产生aes的初始密钥

// 对初始key进行rsa加密得到要传输的密钥enc
AesAndRsaUtil.prototype.encryptkey = function () {
    var encrypt = new JSEncrypt();
    encrypt.setPublicKey(publickey);
    var enc = encrypt.encrypt(this.key);
    return enc;
}

//直接进行aes加密
AesAndRsaUtil.prototype.aesencrypt = function(inputValue) {
    var keybyte = CryptoJS.enc.Hex.parse(key);
    var ivbyte = CryptoJS.enc.Hex.parse(iv);
    var ciphertext = CryptoJS.AES.encrypt(
        inputValue,
        keybyte,
        {iv:ivbyte},
        {mode:CryptoJS.mode.CBC,padding:CryptoJS.pad.Pkcs7});
    return ciphertext.toString();
}




