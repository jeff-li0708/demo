package schema.strategy;

/**
 * Created by liangl on 2019/8/21.
 */
public enum PayChannelEnum {
    WX("wx","微信支付","schema.strategy.WXPayServiceImpl"),
    ZFB("zfb","支付宝支付","schema.strategy.ZFBPayServiceImpl")
    ;
    private final String channel;
    private final String desc;
    private final String clazz;

    PayChannelEnum(String channel, String desc, String clazz) {
        this.channel = channel;
        this.desc = desc;
        this.clazz = clazz;
    }

    public static String getClazzByChannel(String channel) {
        for (PayChannelEnum item : values()) {
            if (channel.equals(item.channel)) {
                return item.clazz;
            }
        }
        return "";
    }
}
