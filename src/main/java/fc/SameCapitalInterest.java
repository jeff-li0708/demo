package fc;

import java.math.BigDecimal;

/**
 * 等额本息
 * Created by liangl on 2019/2/18.
 */
public class SameCapitalInterest {

    public static void main(String[] args) {

        calculate(new BigDecimal(10000),new BigDecimal(0.18),12);
    }

    public static void calculate(BigDecimal capital, BigDecimal rate, Integer period) {

        BigDecimal monthRate = rate.divide(new BigDecimal(12)).setScale(6,BigDecimal.ROUND_UP);
        BigDecimal var1 = monthRate.add(BigDecimal.ONE).pow(period).setScale(6,BigDecimal.ROUND_UP);
        BigDecimal var2 = monthRate.add(BigDecimal.ONE).pow(period).subtract(BigDecimal.ONE).setScale(6,BigDecimal.ROUND_UP);
        System.out.println(monthRate+","+var1+","+var2);
        BigDecimal repaymentAmt = monthRate.multiply(var1).divide(var2, 6, BigDecimal.ROUND_UP).multiply(capital).setScale(2,BigDecimal.ROUND_UP);
        System.out.println(repaymentAmt+",total:"+repaymentAmt.multiply(new BigDecimal(12)));
        BigDecimal remainCapital = capital;
        for (int i = 1;i <= period; i++) {
            BigDecimal var3 = remainCapital.multiply(monthRate);
            BigDecimal var4 = repaymentAmt.subtract(var3);
            remainCapital = remainCapital.subtract(var4);
            System.out.println("第"+i+"期本金："+var4.setScale(2,BigDecimal.ROUND_UP)+",利息："+var3.setScale(2,BigDecimal.ROUND_UP));
        }

    }
}
