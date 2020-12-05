package fc;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 等额本息
 * Created by liangl on 2019/2/18.
 */
public class SameCapitalInterest {

    public static void main(String[] args) {
        BigDecimal monthRate = new BigDecimal(0.14).divide(new BigDecimal(12),6, RoundingMode.HALF_UP);
        System.out.println(monthRate);
        calculate(new BigDecimal(1000000),new BigDecimal(0.06),12*30);
    }

    /**
     *
     * @param capital 本金
     * @param rate 年化（10%的年化则传0.1）
     * @param period 周期（月）
     */
    public static void calculate(BigDecimal capital, BigDecimal rate, Integer period) {

        BigDecimal monthRate = rate.divide(new BigDecimal(12),6, RoundingMode.HALF_UP);
        BigDecimal var1 = monthRate.add(BigDecimal.ONE).pow(period).setScale(6,BigDecimal.ROUND_UP);
        BigDecimal var2 = monthRate.add(BigDecimal.ONE).pow(period).subtract(BigDecimal.ONE).setScale(6,BigDecimal.ROUND_UP);
        System.out.println(monthRate+","+var1+","+var2);
        BigDecimal repaymentAmt = monthRate.multiply(var1).divide(var2, 6, BigDecimal.ROUND_UP).multiply(capital).setScale(2,BigDecimal.ROUND_UP);
        System.out.println("每期："+repaymentAmt);
        BigDecimal remainCapital = capital;
        for (int i = 1;i <= period; i++) {
            BigDecimal var3 = remainCapital.multiply(monthRate);
            BigDecimal var4 = repaymentAmt.subtract(var3);
            remainCapital = remainCapital.subtract(var4);
            System.out.println("第"+i+"期本金："+var4.setScale(2,BigDecimal.ROUND_UP)+",利息："+var3.setScale(2,BigDecimal.ROUND_UP));
        }

    }

    /**
     * 公积金40W30年1740.83
     */
}
