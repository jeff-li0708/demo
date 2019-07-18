package util;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by liangl on 2019/7/17.
 */
public class TextDeal {
    public static void main(String[] args) {
        readFile();
    }

    public static Map<String,Boolean> users = new HashMap<>(1600);
    static {
        try {
            String pathname = "F://uids.txt";
            FileReader reader = new FileReader(pathname);
            BufferedReader br = new BufferedReader(reader);
            String line;
            while ((line = br.readLine()) != null) {
                users.put(line,true);
            }
        }catch (IOException e) {

        }
    }

    /**
     * 读入TXT文件
     */
    public static void readFile() {
        String pathname = "F://2019.06.e.csv";
        File writeName = new File("F://bbbbb.txt");
        try {
            FileReader reader = new FileReader(pathname);
            BufferedReader br = new BufferedReader(reader);
            writeName.createNewFile();
            FileWriter writer = new FileWriter(writeName);
            BufferedWriter out = new BufferedWriter(writer);
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                //String cont = line.substring(line.indexOf("user_avatar"),line.indexOf("type")-3);
                int start = line.lastIndexOf("/")+1;
                int end = line.indexOf("-");
                if (start > 1 && end > start) {
                    String uid = line.substring(start,end);
                    System.out.println(uid);
                    if (users.containsKey(uid)) {
                        out.write(line+"\r\n"); //
                    }
                }
            }
            out.flush(); // 把缓存区内容压入文件
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
