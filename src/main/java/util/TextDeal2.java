package util;

import java.io.*;

/**
 * Created by liangl on 2019/7/17.
 */
public class TextDeal2 {
    public static void main(String[] args) {
        readFile();
    }



    /**
     * 读入TXT文件
     */
    public static void readFile() {
        String pathname = "F://myfile//其他//233790_788223_YjA28ca4dfvC-2019071701//consumer-logs//service-consumer.log";
        File writeNameaa = new File("F://myfile//其他//233790_788223_YjA28ca4dfvC-2019071701//consumer-logs//aa.txt");
        File writeNamebb = new File("F://myfile//其他//233790_788223_YjA28ca4dfvC-2019071701//consumer-logs//bb.txt");
        File writeNamecc = new File("F://myfile//其他//233790_788223_YjA28ca4dfvC-2019071701//consumer-logs//cc.txt");
        try {
            FileReader reader = new FileReader(pathname);
            BufferedReader br = new BufferedReader(reader);
            writeNameaa.createNewFile();
            FileWriter writer1 = new FileWriter(writeNameaa);
            BufferedWriter out1 = new BufferedWriter(writer1);

            writeNamebb.createNewFile();
            FileWriter writer2 = new FileWriter(writeNamebb);
            BufferedWriter out2 = new BufferedWriter(writer2);

            writeNamecc.createNewFile();
            FileWriter writer3 = new FileWriter(writeNamecc);
            BufferedWriter out3 = new BufferedWriter(writer3);
            String line;
            int i = 1;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                if (line.contains("provider-small")) {
                    out1.write(line+"\r\n"); //
                } else if (line.contains("provider-medium")) {
                    out2.write(line+"\r\n"); //
                } else {
                    out3.write(line+"\r\n"); //
                }

            }
            out1.flush(); // 把缓存区内容压入文件
            out2.flush(); // 把缓存区内容压入文件
            out3.flush(); // 把缓存区内容压入文件
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
