<%--
  Created by IntelliJ IDEA.
  User: joseph
  Date: 2017/4/28
  Time: 9:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<!DOCTYPE html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>ContentEncrypt1</title>
    <script src="http://code.jquery.com/jquery-1.8.3.min.js"></script>
    <script src="../../main/webapp/js/crypto-js.js"></script>
    <script src="../../main/webapp/js/jsencrypt.js"></script>
    <script src="../../main/webapp/js/security/AesAndRsaUtil.js"></script>
    <script type="text/javascript">
        var iv = CryptoJS.lib.WordArray.random(128/8).toString(CryptoJS.enc.Hex);//密钥偏移量，用于aes加密
        var key = CryptoJS.lib.WordArray.random(128/8).toString(CryptoJS.enc.Hex);;//用于生成初始key
        /**
         * 实际加密的时候用到的是一个byte数组
         * 如果是一个字符串进行进行加密，CryptoJS会采用加随机salt的形式进行加密
         * 这样在java端就无法进行解密
         * key的值直接采用随机数生成而不是用PBKDF2生成的原因在于所传递的密钥
         * 是用RSA加密过的，与本身生成的密钥关系不大，保密在于知道了传递的密钥也无法破解
         * 如果是aes加解密的话，那么传递的是salt和passphrase，在java端可以生成同样的key
         * 这样就不能保证安全性
         * @type {*}
         */
        function run() {
            var inputValue = $("#inputContent").val();
            var aesAndRsaUtil = new AesAndRsaUtil(iv,key);
            //对key进行rsa加密得到传递的密钥
            var enc = aesAndRsaUtil.encryptkey();
            //对输入进行AES加密
            var ciphertext = aesAndRsaUtil.aesencrypt(inputValue);
            $("#entext").val(ciphertext.toString());
            $("#enc").val(enc);
            $("#iv").val(iv);
        }
    </script>
</head>
<body>
<form id="loginForm" method="post" action="aesandrsade.jsp" onsubmit="run()">

    输入内容<textarea id="inputContent" style="width: 500px;height: 50px;"></textarea><br>
    <input type="hidden" id="entext" name="entext" ></input><br>
    <input type="hidden" id="enc" name="enc" ></input><br>
    <input type="hidden" name="iv" id="iv">
    <input type=submit value="加密" name="encryptBtn" ><br>

</form>
</body>
</html>