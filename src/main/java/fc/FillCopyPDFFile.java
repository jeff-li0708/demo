package fc;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.*;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by liangl on 2018/7/29.
 */
public class FillCopyPDFFile {
    public static void main(String[] args) {
        // 模板路径
        String templatePath = "E:/n_ct_loan_contract.pdf";
        // 生成的新文件路径
        String newPDFPath = "E:/contract_11111.pdf";
        PdfReader reader;
        FileOutputStream out;
        ByteArrayOutputStream bos;
        PdfStamper stamper;
        try {
            out = new FileOutputStream(newPDFPath);// 输出流
            reader = new PdfReader(templatePath);// 读取pdf模板
            bos = new ByteArrayOutputStream();
            stamper = new PdfStamper(reader, bos);
            AcroFields form = stamper.getAcroFields();

            Map<String,String> map=new HashMap();
            map.put("fill_1","NO888888"); //第几号
            map.put("fill_2","张三"); //借款人（乙方）
            map.put("fill_3","511502197811016666"); //身份证号码
            map.put("fill_4","成都是高新区天府软件园G3"); //住所地
            map.put("fill_5","13866666666"); //联系电话
            map.put("fill_6","600000"); //邮政编码
            map.put("fill_7","电商购物"); //用途
            map.put("fill_8","5000.00"); //借款金额为
            map.put("fill_9",MoneyUtil.toChinese("5000.00")); //借款金额大写
            map.put("fill_10","12"); //借款期限（月）
            map.put("fill_11","2018"); //自xxx年
            map.put("fill_12","01"); //xxx月
            map.put("fill_13","01"); //xxx日起
            map.put("fill_14","2019"); //至xxx年
            map.put("fill_15","01"); //xxx月
            map.put("fill_16","01"); //xxx日止
            map.put("fill_17","张三"); //账户户名
            map.put("fill_18","6222000011111442222"); //账号
            map.put("fill_19","中国银行"); //银行
            map.put("fill_20","张三"); //借款人（乙方）
            map.put("fill_21","张三"); //有权签字人
            map.put("fill_22","2018"); //贷款人签字下面的年
            map.put("fill_23","01"); //贷款人签字下面的月
            map.put("fill_24","01"); //贷款人签字下面的日
            map.put("fill_25","2018"); //借款人签字下面的年
            map.put("fill_26","01"); //借款人签字下面的月
            map.put("fill_27","01"); //借款人签字下面的日


            BaseFont bf = BaseFont.createFont("STSong-Light",
                    "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            //Font font = new Font(bf, 14, Font.NORMAL);
            //form.addSubstitutionFont(bf);

            java.util.Iterator<String> it = form.getFields().keySet().iterator();
            while (it.hasNext()) {
                String name = it.next().toString();
                System.out.println(name);
                form.setFieldProperty(name,"textfont",bf,null);
                form.setField(name, map.get(name));
            }
            //        AcroFields form = ps.getAcroFields();
            //        for (String key : param.keySet()) {
            //            form.setField(key, param.get(key).toString());
            //        }
            stamper.setFormFlattening(true);// 如果为false那么生成的PDF文件还能编辑，一定要设为true
            stamper.close();

            Document doc = new Document();
            PdfCopy copy = new PdfCopy(doc, out);
            doc.open();
            PdfReader reader1 = new PdfReader(bos.toByteArray());
            int page = reader1.getNumberOfPages();
            for(int i = 1;i <= page; i++) {
                PdfImportedPage importPage = copy.getImportedPage(reader1, i);
                copy.addPage(importPage);
            }
            doc.close();

        } catch (IOException e) {
            System.out.println(1);
        } catch (DocumentException e) {
            System.out.println(2);
        }
    }
}
