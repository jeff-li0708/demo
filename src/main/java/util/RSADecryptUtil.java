package util;


import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * Created by liangl on 2018/1/29.
 */
public class RSADecryptUtil {
    static Logger logger = LoggerFactory.getLogger(RSADecryptUtil.class);


    private static PublicKey publicKey;


    private static PrivateKey privateKey;
    static {

        try {
//            String publicKeyString = DoveUtil.getYyPublicKey();
//            String publicKeyString = ZookeeperHelper.getTable("jm-finance.fql.interface").get("pubkey").toString();
            String publicKeyString = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDEAUgEDNYaHXG7t+qiC8OwqarE" +
                    "CrkpwcPuLNVf8mDhVGOaLqY71FnYmO5E/r4xkNEczmwZrouKpEbyO/Nr8iD3OEKK" +
                    "GKYRTtO7YJXEhglC5om/YoLuWyA4kfuD75EdtXD6d2pGIZlnnVyT39jl/Oq+My5v" +
                    "ji375L3WuyEMP6RpRQIDAQAB";
            KeySpec keySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKeyString));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            publicKey = keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }

        try {
            //String privateKeyString = DoveUtil.getYyPrivateKey();
//            String privateKeyString = ZookeeperHelper.getTable("jm-finance.fql.interface").get("privateKey").toString();
            String privateKeyString = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMQBSAQM1hodcbu3" +
                    "6qILw7CpqsQKuSnBw+4s1V/yYOFUY5oupjvUWdiY7kT+vjGQ0RzObBmui4qkRvI7" +
                    "82vyIPc4QooYphFO07tglcSGCULmib9igu5bIDiR+4PvkR21cPp3akYhmWedXJPf" +
                    "2OX86r4zLm+OLfvkvda7IQw/pGlFAgMBAAECgYAsebn0qVD/1gSTuvTz1kWOVcGZ" +
                    "AHHppr0l02iLf/05xCNhvgocc1/7xR/z6fYQWzMIHVLsNO4XBJXQq4fdy7Bs4r10" +
                    "ukJDXaoNynRenpo4HGa3rURlTbKrOFthOEsGdKvYK9VZK6XXXRXm5wumOuJrwc0R" +
                    "McRLbVZ6mRUH/OCfIQJBAOvJNWsHmF0V01hiGbtxLnAIu/QkJ5sMoQE3fcAWdh+A" +
                    "Q+I1IYml+Fjdzqsaii/8AAYmNs68EH78JYirJvKqFZ0CQQDUzwAXCsbTLbDeCkyG" +
                    "Kxk4f+A6MPDj6wS00fmxdpQ92PFBkc9HCqPO4TRLIWPdco/mILN5cQnJrCP1O/Mk" +
                    "lOXJAkBul2JhwB870efeNa0iGNUSIVafDXIyLDs8Sd3GggKHhe7RI+CGIn2YmPi9" +
                    "kmYsWYNdXwEn+Fe4Z+Xm83gCK7qVAkEA0dgMzqvhwvubHFgrfWGiTQRLpxX7c8Ob" +
                    "sHIdNdEn/JSf0XhmXDcxsB6B4lPaldMR8mrsjyPX8vW7v2aOK9RngQJBAJrsRGp3" +
                    "wZ6B+UL+IYRzrkjdBqGudKGQvji2yllStNsK8WGUo/DVF/bzOnIMvVO+5dc30csV" +
                    "XPLyrO4NTXaQYEk=";
            Security.addProvider(
                    new org.bouncycastle.jce.provider.BouncyCastleProvider()
            );
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKeyString));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            privateKey = keyFactory.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }


    }



    public static byte[] yYRSAByPubKey(String param){
        try {
            return SecurityUtils.rsaDecode(Base64.decodeBase64(param), publicKey);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] yYRSAByPrivateKey(String param){
        try {
            return SecurityUtils.rsaDecode(Base64.decodeBase64(param), privateKey);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String decode(String param) throws UnsupportedEncodingException {
        byte[] rsaCode = yYRSAByPrivateKey(param);
        return new String(rsaCode);
    }

    /**
     * RSA签名
     * @param content 待签名数据
     * @param input_charset 编码格式
     * @return 签名值
     */
    public static String sign(String content, String input_charset)
    {
        try
        {
            Signature signature = Signature
                    .getInstance("SHA1WithRSA");
            signature.initSign(privateKey);
            signature.update(content.getBytes(input_charset));
            byte[] signed = signature.sign();
            return Base64.encodeBase64String(signed);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        String decodeStr = decode("ZdYTKuxM0ZLJmKUWZ6fBLB5xJ3HdPG1leBH1Cfg6c66RyQSyKh7t1u8g69HTS90x192i02pMql1v5j8m7aU57E4ndHNa8wv2W51E6kj5VoIp/bpiWfckhsLWLAfkmktH2RgGZoAJkdXTT7Nx/jXKnN5xcSiX1osdu8NhUMkHxSJtfScw0NmXOdcqJnG5snV/bPb4kbhp3IwylgFubL8keVMzW3w3a4NWmjbsZqNjLsZNKOU/iPlL5WgwsFAbnKTZNXyiI/0CKjYpm9JucRhIIDpifnoTYMYHr2FIQ9dc7fiDE5O/YM93S/a5ZxY69lDbR+hZu7HFxX9vzKY9sRouXQ==");
        System.out.println(decodeStr);
    }
}
