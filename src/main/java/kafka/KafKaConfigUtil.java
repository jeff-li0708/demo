package kafka;

/**
 * Created by liangl on 2019/7/25.
 */
public class KafKaConfigUtil {
    public static final String KAFKA_SERIALIZER_CLASS = "kafka.serializer.StringEncoder";
    public static final String KAFKA_PRODUCER_TYPE = "async";
    public static final String KAFKA_BATCH_NUM_MESSAGES = "2000";
    public static final String KAFKA_REQUEST_REQUIRED_ACKS = "0";
    public static final String KAFKA_ZOOKEEPER_SESSION_TIMEOUT = "5000";
    public static final String KAFKA_ZOOKEEPER_SYNC_TIME = "200";
    public static final String KAFKA_AUTO_COMMIT_INTERVAL = "1000";
//	public static final String KAFKA_TOPIC_PRAISE = "show_praise";
    /**
     * 帖子topic
     */
    public static final String KAFKA_TOPIC_SHOW = "show_post";
}
