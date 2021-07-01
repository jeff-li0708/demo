package algorithm;

import java.util.*;

public class AA {
    public static void main(String[] args) {
//        String s = "wordgoodgoodgoodbestword";
//        String[] words = {"word","good","best","good"};
//        String s = "aaa";
//        String[] words = {"a","a"};
        HashSet<Integer> set = new LinkedHashSet();
        set.clear();
        String s = "barfoofoobarthefoobarman";
        String[] words = {"bar","foo","the"};
//        String s = "aaaaaaaaaaaaaa"; //1,2,3,4,5,6,7,8,9,10
//        String[] words = {"aa","aa"};
        List<Integer> result = new ArrayList<>();
        int wl = words[0].length();
        int twl = wl * words.length;
        Map<String,Integer> wordMap = new HashMap();
        for (String word : words) {
            wordMap.put(word,wordMap.get(word) == null ? 1:wordMap.get(word)+1);
        }
        for (int i = 0; i < s.length()-twl+1; i++) {
            Map<String,Integer> tempWordMap = new HashMap();
            int count = words.length;
            for (int r = i; r-i <= twl && r+wl <= s.length();) {
                String subStr = s.substring(r,r+wl);
                if (wordMap.containsKey(subStr)) {
                    if (!tempWordMap.containsKey(subStr)) {
                        tempWordMap.put(subStr,1);
                        r = r +wl;
                        count--;
                    } else if (tempWordMap.get(subStr) < wordMap.get(subStr)) {
                        tempWordMap.put(subStr,tempWordMap.get(subStr)+1);
                        r = r +wl;
                        count--;
                    } else {
                        break;
                    }
                } else {
                    break;
                }
            }
            if (count == 0) {
                result.add(i);
            }
        }
        System.out.println(result);
    }
}
