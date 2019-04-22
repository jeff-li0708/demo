package com.yeepay.common.securityplatform;


import com.cfca.util.pki.api.CertUtil;
import com.cfca.util.pki.api.KeyUtil;
import com.cfca.util.pki.api.SignatureUtil;
import com.cfca.util.pki.cert.X509Cert;
import com.cfca.util.pki.cipher.JCrypto;
import com.cfca.util.pki.cipher.JKey;
import com.yeepay.sqkkseperator.config.Config;
import org.apache.log4j.Logger;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.HashMap;
import java.util.Map;


public class YBPayUtil {

    public static Logger logger = Logger.getLogger(YBPayUtil.class);

    private static Config config = Config.getInstance();


    /**
     * XML格式字符串转换为Map
     *
     * @param strXML XML字符串
     * @return XML数据转换后的Map
     * @throws Exception
     */
    public static Map<String, String> xmlToMap(String strXML) throws Exception {
        Map<String, String> data = new HashMap<String, String>();
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder= documentBuilderFactory.newDocumentBuilder();
        InputStream stream = new ByteArrayInputStream(strXML.getBytes("GBK"));
        org.w3c.dom.Document doc = documentBuilder.parse(stream);
        doc.getDocumentElement().normalize();
        NodeList nodeList = doc.getDocumentElement().getChildNodes();
        for (int idx=0; idx<nodeList.getLength(); ++idx) {
            Node node = nodeList.item(idx);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                org.w3c.dom.Element element = (org.w3c.dom.Element) node;
                data.put(element.getNodeName(), element.getTextContent());
            }
        }
        try {
            stream.close();
        } catch (Exception ex) {
        }
        return data;
    }

    /**
     * 将Map转换为XML格式的字符串
     *
     * @param data Map类型数据
     * @return XML格式的字符串
     * @throws Exception
     */
    public static String mapToXml(Map<String, String> data) throws Exception {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder= documentBuilderFactory.newDocumentBuilder();
        org.w3c.dom.Document document = documentBuilder.newDocument();
        org.w3c.dom.Element root = document.createElement("data");
        document.appendChild(root);
        for (String key: data.keySet()) {
            String value = data.get(key);
            if (value == null) {
                value = "";
            }
            value = value.trim();
            org.w3c.dom.Element filed = document.createElement(key);
//            CDATASection cdata = document.createCDATASection(value);
//            filed.appendChild(cdata);
            filed.appendChild(document.createTextNode(value));
            root.appendChild(filed);
        }
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        DOMSource source = new DOMSource(document);
        transformer.setOutputProperty(OutputKeys.ENCODING, "GBK");
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        transformer.transform(source, result);
        String output = writer.getBuffer().toString(); //.replaceAll("\n|\r", "");
        try {
            writer.close();
        }
        catch (Exception ex) {
        }
        return output;
    }

    /**
     * 签名
     * @param hmacStr
     * @return
     * @throws Exception
     */
    public static String yibaoSigin(String hmacStr) throws Exception {
        //下面用数字证书进行签名
        com.cfca.util.pki.cipher.Session tempsession = null;
        String ALGORITHM = SignatureUtil.SHA1_RSA;
        JCrypto jcrypto =null;
        if(tempsession==null){
            try {
                //初始化加密库，获得会话session
                //多线程的应用可以共享一个session,不需要重复,只需初始化一次
                //初始化加密库并获得session。
                //系统退出后要jcrypto.finalize()，释放加密库
                jcrypto = JCrypto.getInstance();
                jcrypto.initialize(JCrypto.JSOFT_LIB, null);
                tempsession = jcrypto.openSession(JCrypto.JSOFT_LIB);
            } catch (Exception ex) {
                System.out.println(ex.toString());
            }
        }

        InputStream is = config.getCertStream();
        byte[] bytes = toByteArray(is);
        JKey jkey = KeyUtil.getPriKey(bytes, "123456");
        X509Cert cert = CertUtil.getCert(bytes, "123456");

        X509Cert[] cs=new X509Cert[1];
        cs[0]=cert;
        String signMessage ="";
        SignatureUtil signUtil =null;
        try{
            // 第二步:对请求的串进行MD5对数据进行签名
            String yphs = com.yeepay.common.securityplatform.Digest.hmacSign(hmacStr);
            signUtil = new SignatureUtil();
            byte[] b64SignData;
            // 第三步:对MD5签名之后数据调用CFCA提供的api方法用商户自己的数字证书进行签名
            b64SignData = signUtil.p7SignMessage(true, yphs.getBytes(),ALGORITHM, jkey, cs, tempsession);
            if(jcrypto!=null){
                jcrypto.finalize (com.cfca.util.pki.cipher.JCrypto.JSOFT_LIB,null);
            }
            signMessage = new String(b64SignData, "UTF-8");
        }catch(Exception e){
            logger.info("易宝签名错误",e);
        } finally {
            is.close();
        }
        System.out.println("经过md5和数字证书签名之后的数据为---||"+signMessage+"||");
        return signMessage;
    }

    /**
     * 生成带签名的xml字符串
     * @param param
     * @param needSiginFields
     * @return
     * @throws Exception
     */
    public static String generateSignedXml(Map<String,String> param,String[] needSiginFields) throws Exception {
        //拼接待签名串
        StringBuilder sb = new StringBuilder();
        for (String str : needSiginFields) {
            sb.append(param.get(str));
        }
        sb.append(config.getValue("keyValue"));

        try {
            param.put("hmac", YBPayUtil.yibaoSigin(sb.toString())); //签名
        } catch (Exception e) {
            logger.info("查询商户余额签名错误", e);
        }
        return mapToXml(param);
    }
    public static byte[] toByteArray(InputStream input) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
        }
        return output.toByteArray();
    }

}
