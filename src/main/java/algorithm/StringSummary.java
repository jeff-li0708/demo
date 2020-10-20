package algorithm;

public class StringSummary {

    /**将字符串中的空格替换为%20*/
    public String replaceSpace(String s) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0;i< s.length();i++){
            char c = s.charAt(i);
            sb.append(c == ' ' ? "%20" : c);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        int n = 0b10000000000000000000000000000000;
        System.out.println(n);
//        while (n != 0 && n !=-1){
//            System.out.println(n);
//            n>>=1;
//        }
//        String s = "123";
//        System.out.println(s.substring(0,2));
//        StringSummary obj = new StringSummary();
//        System.out.println(obj.replaceSpace(" 1"));

    }
}
