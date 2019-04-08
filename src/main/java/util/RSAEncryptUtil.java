package util;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * RSA加密工具类
 *
 * @author liangl
 * @create 2018-01-07 11:18
 **/
public class RSAEncryptUtil {

    static Logger logger = LoggerFactory.getLogger(RSAEncryptUtil.class);


    private static PublicKey publicKey;


    private static PrivateKey privateKey;
    static {

        try {
            //String publicKeyString = DoveUtil.getYyPublicKey();
//            String publicKeyString = ZookeeperHelper.getTable("jm-finance.fql.interface").get("pubkey").toString();
            String publicKeyString = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDTkJZfBU1+TK5RAKyS7WSn4wLC" +
                  "nTZ3npnPMdMseoxkAO7aQiCuc8Y3DdhB7KqfHoHnO5tiw/CpO+9clxBP2dAg+hvS" +
                  "fdVxqEfhehsqsZdPyi1LrbtXNIE6a/8rouClffEPXT0/z0/f6NBK0TwoNqSJFEmI" +
                  "YvykoU4bdxOpYLk5qQIDAQAB";
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
            String privateKeyString = "MIICXQIBAAKBgQCnfEVYhoo29MZZILnx/uEHVtpMNdIvBRbJua+ic1wCfWTxYmvF0wVZrr/9SUlNYXNKU2glg8QS2mxkBZwEewLsbIXNRjBPt21Wa+dYyby5eL2f91gqoiiZUw75qpOFAWFMWMjUY9s5Nl6xtR1drIC3dV/oIjg6FXgQtQlNp8oP3QIDAQAB" +
                    "AoGAFgGTDv2mfXAN+7n8FJrbsF4uEqNjuX/eraSzOhIUPdOCBuFarcR7SHxYh4ww" +
                    "kHVEc7ifz4YPpjaC1WWTSVdsW7O37W6SMYRSI95FvWFSY7FK1GQYyTkerFTey/xm" +
                    "sSMH1B3XQhONDCLjmNtZ1Ho5B58TuvLu8v3HUsSaCAEW8oECQQDWggGbJ0V45ai3" +
                    "NCDez88PNbWEpcxNt43Yf7KJ47njFB9xDreqxbIt3kzhwtUw1U4FIaLgemCn/crB" +
                    "7rl+978hAkEAx+HPdWoiOHmzOSgBgtHkJhbOB2XlGJlvBsKBgqeaErVA8+AMpSHJ" +
                    "CM6HwluUKkj7fvLn22LhKlbZaOXSHKnlPQJBAJevMQGNAKRgCjEarI/CdVH1Edmu" +
                    "AE2ImcZZbl/xZ7Y6uY0R8jDRQZEL3GPciTLR8yzPv0RrZUQFxEXk0MkG2eECQH5a" +
                    "DB6Y3ND+fYDf/TSJIcPCHrMmgk+ujjSjt+Kzt/2jfk+rjdLFLKPYipEd+udQA0sY" +
                    "meBg/R0akYxx2wQrkbECQQCHsC5fD2SMvoLGrDqEr9Cq5yITP2nGdDfMekS50pRg" +
                    "jG0e4ZiSK9bZW744ktEBCORcmmJChlDp34mdTN0ATA4F";
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
            return SecurityUtils.rsaEncode(param.getBytes(), publicKey);
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
            return SecurityUtils.rsaEncode(param.getBytes(), privateKey);
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
            signature.update( content.getBytes(input_charset) );
            byte[] signed = signature.sign();
            return Base64.encodeBase64String(signed);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }


//    public static String yYBase64(String value){
//        return Base64.encodeBase64String(value);
//    }


    public static String encode(String param) throws UnsupportedEncodingException {
        byte[] rsaCode = yYRSAByPubKey(param);
//        System.out.println(new String(rsaCode,"utf-8"));
        String base64Code = Base64.encodeBase64String(rsaCode);
        return base64Code;
    }


    public static void main(String[] args) throws UnsupportedEncodingException {
        String param = "open_id="+ URLEncoder.encode("1234567","utf-8");
        System.out.println(param);
        Map<String,Object> map = new HashMap<>();
        map.put("merchant_id","2017120501");
        map.put("product_id","jumei");
        map.put("version","01");
        map.put("biz_data",URLEncoder.encode(encode(param),"utf-8"));
        map.put("sign",sign(param,"utf-8"));
        System.out.println(sign(param,"utf-8"));
//        String result = HttpClientUtil.doPost("http://test.bluecard1000.com/api/risk/user",map);
//        System.out.println(result);
    }

}
