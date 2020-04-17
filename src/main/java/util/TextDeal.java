package util;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by liangl on 2019/7/17.
 */
public class TextDeal {
    public static void main(String[] args) {
        //readFile();
        String str = AAAA.getStr("1111");
        System.out.println(str);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                String str = AAAA.getStr("222");
                System.out.println(str);
            }
        });
        thread.start();
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                String str = AAAA.getStr("222");
                System.out.println(str);
            }
        });
        thread2.start();
    }

    public static Map<String,Boolean> users = new HashMap<>(1600);
    static {
        try {
            String pathname = "C:\\Users\\jumei\\Desktop\\uid.txt";
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
        String pathname = "E:\\dbman_4427_txt.txt";
        File writeName = new File("C:\\Users\\jumei\\Desktop\\bbbbb.txt");
        try {
            FileReader reader = new FileReader(pathname);
            BufferedReader br = new BufferedReader(reader);
            writeName.createNewFile();
            FileWriter writer = new FileWriter(writeName);
            BufferedWriter out = new BufferedWriter(writer);
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
                String[] strArr = line.split("   ");
                if (users.containsKey(strArr[1].trim())) {
                    out.write(line+"\r\n"); //
                }

            }
            out.flush(); // 把缓存区内容压入文件
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
