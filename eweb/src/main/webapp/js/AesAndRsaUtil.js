/**
 * Created by joseph on 2017/4/17.
 * js只负责aes加密和对密钥的aes加密
 * java端负责对aes的解密和rsa的解密
 */
var publickey ="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCTTCzKuBWRlna6ILcVpCTFN93H26h3fEvszY6RpKjTa0UhYa/yp6I87ST55z4F2IaTNiBZPajoRfsw19hLjiaK2jpV2+L8OA+LMXxpLZWVydZSdJv2e6o7a+WxK9SSc2HZZQJkNXguTdo3bXQ9knA28eI/p6MCjZh0BjKhNKSpoQIDAQAB";
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




