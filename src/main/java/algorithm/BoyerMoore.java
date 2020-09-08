package algorithm;

/**
 * 在一个字符串s中查找某个子串str
 */
public class BoyerMoore {
    public static void main(String[] args) {
        String s = "if(\"uploadFileTask\".equals(delegateTask.getTaskDefinitionKey())){\n" +
                "                String liveReconciliationLetter = jedisCluster.srandmember(RedisConstants.SHUABAO_MCN_LIVE_RECONCILIATION_LETTER_SET + reconciliationNo);\n" +
                "                if (StringUtils.isNotEmpty(liveReconciliationLetter)){\n" +
                "                    jedisCluster.sadd(RedisConstants.SHUABAO_MCN_LIVE_RECONCILIATION_LETTER_LOG_SET + reconciliationNo, liveReconciliationLetter);\n" +
                "                    jedisCluster.del(RedisConstants.SHUABAO_MCN_LIVE_RECONCILIATION_LETTER_SET + reconciliationNo);\n" +
                "                }\n" +
                "            }";
        String str = "liveR";
        for(int i = str.length()-1;i<s.length();){
            if (str.charAt(str.length()-1) != s.charAt(i)){
                int j = 1;
                for (;j<str.length();j++){
                    if(s.charAt(i) == str.charAt(str.length()-1-j)){
                        break;
                    }
                }
                i += j;
            } else { //好后缀
                int j = 1;
                for (;j<str.length();j++){
                    if(s.charAt(i-j) != str.charAt(str.length()-1-j)){
                        break;
                    }
                }
                if (str.length() == j) {
                    System.out.println(i-j+1);
                    return;
                } else {
                    i += j;
                }
            }
        }
    }
}
