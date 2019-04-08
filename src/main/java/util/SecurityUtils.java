/*
 * 
 */
package util;

import org.apache.commons.codec.binary.Base64;
import org.springframework.stereotype.Component;

import javax.crypto.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

// TODO: Auto-generated Javadoc
/**
 * The Class SecurityUtils.
 *
 * @author yuyue MD5 SHA1 encode and decode utils
 * @version add triple des and rsa
 */

/**
 * @author JM
 */
@SuppressWarnings("restriction")
@Component
public class SecurityUtils {

    /**  ----------------------Triple DES CONFIG---------------------------------. */
    /**
     * 提供对称密钥生成器的功能，支持各种算法
     */
    private static KeyGenerator keygen;

    /**
     * 负责保存对称密钥.
     */
    private static SecretKey deskey;

    /**
     * 负责完成加密或解密工作.
     */
    private static Cipher tripleDESCipher;

    /**  ----------------------Triple DES CONFIG---------------------------------. */


    /** ----------------------RSA CONFIG--------------------------------- */

    static {
        try {
            // 初始化Triple Des加密的驱动/Cipher，生成对称加密的deskey
            Security.addProvider(new com.sun.crypto.provider.SunJCE());
            keygen = KeyGenerator.getInstance("AES");
            deskey = keygen.generateKey();
            tripleDESCipher = Cipher.getInstance("AES");

            // KeyPairGenerator 类用于生成公钥和私钥对，基于RSA算法生成对象
            // Cipher负责完成加密或解密工作，基于RSA
 //           RSACipher = Cipher.getInstance("RSA");
//            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
//            keyPairGen.initialize(1024);
//            KeyPair keyPair = keyPairGen.generateKeyPair();
//            privateKey = (RSAPrivateKey) keyPair.getPrivate();
//            publicKey = (RSAPublicKey) keyPair.getPublic();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 支持MD5和SHA1加密方式。类型由参数arithmetic决定。.
     *
     * @param str        the str
     * @param arithmetic UnionConst.MD5\UnionConst.SHA1
     * @return the string
     * @throws NoSuchAlgorithmException     the no such algorithm exception
     * @throws UnsupportedEncodingException the unsupported encoding exception
     */
    public static String encode(String str, String arithmetic) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest messageDigest = null;
        messageDigest = MessageDigest.getInstance(arithmetic);
        messageDigest.reset();
        messageDigest.update(str.getBytes("UTF-8"));

        byte[] byteArray = messageDigest.digest();

        StringBuffer md5StrBuff = new StringBuffer();

        for (int i = 0; i < byteArray.length; i++) {
            if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
                md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
            else
                md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
        }

        return md5StrBuff.toString();
    }

    /**
     * 3DES加密.
     *
     * @param str 加密字符
     * @return the byte[]
     * @throws InvalidKeyException       the invalid key exception
     * @throws IllegalBlockSizeException the illegal block size exception
     * @throws BadPaddingException       the bad padding exception
     */
    public static byte[] tripleDESEncode(String str) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        byte[] cipherByte;
        // 根据密钥，对Cipher对象进行初始化，ENCRYPT_MODE表示加密模式
        tripleDESCipher.init(Cipher.ENCRYPT_MODE, deskey);
        byte[] src = str.getBytes();
        // 加密，结果保存进cipherByte
        cipherByte = tripleDESCipher.doFinal(src);
        return cipherByte;
    }

    /**
     * 3DES解密.
     *
     * @param buff the buff
     * @return the byte[]
     * @throws InvalidKeyException       the invalid key exception
     * @throws IllegalBlockSizeException the illegal block size exception
     * @throws BadPaddingException       the bad padding exception
     */
    public static byte[] tripleDESDecode(byte[] buff) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        byte[] cipherByte;
        // 根据密钥，对Cipher对象进行初始化，DECRYPT_MODE表示加密模式
        tripleDESCipher.init(Cipher.DECRYPT_MODE, deskey);
        cipherByte = tripleDESCipher.doFinal(buff);
        return cipherByte;
    }

    /**
     * RSA加密.
     *
     * @param srcBytes the src bytes
     * @return the byte[]
     * @throws NoSuchAlgorithmException  the no such algorithm exception
     * @throws NoSuchPaddingException    the no such padding exception
     * @throws InvalidKeyException       the invalid key exception
     * @throws IllegalBlockSizeException the illegal block size exception
     * @throws BadPaddingException       the bad padding exception
     */
//    public static byte[] rsaEncode(byte[] srcBytes) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException,
//            BadPaddingException {
//        if (publicKey != null) {
//            // 根据公钥，对Cipher对象进行初始化
//            RSACipher.init(Cipher.ENCRYPT_MODE, publicKey);
//            byte[] resultBytes = RSACipher.doFinal(srcBytes);
//            return resultBytes;
//        }
//        return null;
//    }

    /**
     * @TODO 分块编码数据
     * @param srcBytes  原数组
     * @param blockLength  分组大小
     * @param cipher 加解密对象
     * @return
     * @throws IOException
     * @throws BadPaddingException
     * @throws IllegalBlockSizeException
     */
    private static byte[] codeRasData(byte[] srcBytes,int blockLength,Cipher cipher) throws IOException, BadPaddingException, IllegalBlockSizeException {
        int inputLen = srcBytes.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > blockLength) {
                cache = cipher.doFinal(srcBytes, offSet, blockLength);
            } else {
                cache = cipher.doFinal(srcBytes, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            offSet += blockLength;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }
    /**
     * @param srcBytes
     * @param privateKey 私匙
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @TODO RSA私匙加密
     */
    public static byte[] rsaEncode(byte[] srcBytes, PrivateKey privateKey) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException,
            BadPaddingException, IOException {
        if (privateKey == null) {
            throw new RuntimeException("private key is null", null);
        }
        // 根据公钥，对Cipher对象进行初始化
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        return codeRasData(srcBytes,117,cipher);
    }

    /**
     * @param srcBytes
     * @param publicKey
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @TODO RSA公匙加密
     */
    public static byte[] rsaEncode(byte[] srcBytes, PublicKey publicKey) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException,
            BadPaddingException, IOException {
        if (publicKey == null) {
            throw new RuntimeException("public key is null", null);
        }
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return codeRasData(srcBytes,117,cipher);
    }

    /**
     * @param srcBytes
     * @param privateKey 解密私匙
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @TODO RSA私匙解密
     */
    public static byte[] rsaDecode(byte[] srcBytes, PrivateKey privateKey) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException,
            BadPaddingException, IOException {
        if (privateKey == null) {
            throw new RuntimeException("private key is null", null);
        }
        // 根据公钥，对Cipher对象进行初始化
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return codeRasData(srcBytes,128,cipher);
    }

    /**
     * @param srcBytes  需要解密信息
     * @param publicKey 解密公匙
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     * @TODO RSA 公匙解密
     */
    public static byte[] rsaDecode(byte[] srcBytes, PublicKey publicKey) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException,
            BadPaddingException, IOException {
        if (publicKey == null) {
            throw new RuntimeException("public key is null", null);
        }
        // 根据公钥，对Cipher对象进行初始化
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        return codeRasData(srcBytes,128,cipher);
    }


    /**
     * RSA解密.
     *
     * @param srcBytes the src bytes
     * @return the byte[]
     * @throws NoSuchAlgorithmException  the no such algorithm exception
     * @throws NoSuchPaddingException    the no such padding exception
     * @throws InvalidKeyException       the invalid key exception
     * @throws IllegalBlockSizeException the illegal block size exception
     * @throws BadPaddingException       the bad padding exception
     */
//    public static byte[] rsaDecode(byte[] srcBytes) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException,
//            BadPaddingException {
//        if (privateKey != null) {
//            RSACipher.init(Cipher.DECRYPT_MODE, privateKey);
//            byte[] resultBytes = RSACipher.doFinal(srcBytes);
//            return resultBytes;
//        }
//        return null;
//    }

    /**
     * 新建公钥.
     *
     * @return the string
     */
    public static String createSecuritKey() {
        return new String(keygen.generateKey().getEncoded());
    }

    /**
     * base64加密.
     *
     * @param src the src
     * @return the string
     */
    public static String base64Encode(String src) {
        try {
            return new String(Base64.encodeBase64(src.getBytes("UTF-8")));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * base64解密.
     *
     * @param src the src
     * @return the string
     */
    public static String base64Decode(String src) {
        try {
            return new String(Base64.decodeBase64(src.getBytes("UTF-8")));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws InvalidKeySpecException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, NoSuchPaddingException, IOException {
        //String publicKeyStr = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDFqRaLlM4Hnq4RrUUdVHNYHzFGbiWv/nAGItFe2jX4nmOhwuTUMiFkPAZq4oT1YYp45xl/2eiPSFDc/Mb0d+I1jYkqqKmirqzLLwaFNWhqEWLx+tBShPq4HnhklcjyD+3AJN9ilFsGJrn6Xbl7xG17mFbyeb0+9WFPfaXkblx1EQIDAQAB";
        //String privateKeyStr = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMWpFouUzgeerhGtRR1Uc1gfMUZuJa/+cAYi0V7aNfieY6HC5NQyIWQ8BmrihPVhinjnGX/Z6I9IUNz8xvR34jWNiSqoqaKurMsvBoU1aGoRYvH60FKE+rgeeGSVyPIP7cAk32KUWwYmufpduXvEbXuYVvJ5vT71YU99peRuXHURAgMBAAECgYEAwG5Ed7Fv/3UZbDYu8kX0YVdExpeXc0aXUzH0eY6hOqEgyCwQURFY9teFO+u5m5bRk7ERXyoLQesaZnplSz2nZ9iA+3CWZsVZWC/wBNzhz245CzaCqhhFzneZS7QgxOLrccNbNEZX08KxHqzikTYCRgdpssEuq81MG+txidYnXgECQQD9W2BYGH+K0Z2pohEtxd7rxKGPjl2gt/QDe4DZHCQ4UvRYcWmW5//C8IQPOkf9LkLTmmayKRxLSodN577+bYHhAkEAx7j3iBgxCSxvDLVFU5LxOJQxeDCf9V2oaRnq8QWymHgRAyyZ0qpLb64XqMYXkrxCK7LSy2/T5OHfzxp8P+u5MQJBALAzu7apGxLlo7wVPdNSuS3Jq/8WKSGcrfU6EKtuOfZ+IvfbsG7GXQcrlPyuMODxKoLV8DRaCRaH98ADu75hVKECQGJUdLoyfuHvWHU/QfSVoeGjRH/SIM7i36lvMzabAYUDiAyyBsGrShyf79IORUjfGwz8q7+xPPexJUmH1qh52+ECQHBNIW5SH818PUaqxxjKx/rOmtoU0LRYSafOVZP65N6xrWjcqIJWcnfp3IYNzMd25KBKFUc5G8eOXZ6Hkf/KcdQ=";
        String publicKeyStr = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDEAUgEDNYaHXG7t+qiC8OwqarE" +
                "CrkpwcPuLNVf8mDhVGOaLqY71FnYmO5E/r4xkNEczmwZrouKpEbyO/Nr8iD3OEKK" +
                "GKYRTtO7YJXEhglC5om/YoLuWyA4kfuD75EdtXD6d2pGIZlnnVyT39jl/Oq+My5v" +
                "ji375L3WuyEMP6RpRQIDAQAB";
        String privateKeyStr = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAMQBSAQM1hodcbu3" +
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
        KeySpec keySpec = new X509EncodedKeySpec(Base64.decodeBase64(publicKeyStr));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);

        keySpec = new PKCS8EncodedKeySpec(Base64.decodeBase64(privateKeyStr));
        keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

        String message = "openId=5000&amount=5000&orderId=5000";


        byte[] encodeBytes = rsaEncode(message.getBytes(), publicKey);

        String encodeStr = Base64.encodeBase64String(encodeBytes);
        //encodeStr = URLEncoder.encode(encodeStr,"UTF-8");
        System.out.println(encodeStr);


        //byte[] decodeBytes = rsaDecode(Base64.decodeBase64(URLDecoder.decode(encodeStr, "UTF-8")),publicKey);
        byte[] decodeBytes = rsaDecode(Base64.decodeBase64(encodeStr),privateKey);
        String decodeStr = new String(decodeBytes);
        System.out.println(decodeStr);

    }
}
