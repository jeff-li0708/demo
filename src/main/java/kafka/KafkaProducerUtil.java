package kafka;

import com.alibaba.fastjson.JSON;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.ExecutorUtils;
import util.SystemConf;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class KafkaProducerUtil {
    private static Logger logger = LoggerFactory.getLogger(KafkaProducerUtil.class);
    private static KafkaProducerUtil instance;
    private static Properties pros = new Properties();
    private Producer<Integer, String> producer;

    private KafkaProducerUtil() {
        pros.put("serializer.class", KafKaConfigUtil.KAFKA_SERIALIZER_CLASS);
        pros.put("metadata.broker.list", SystemConf.get("Kafka_service_value"));
        // pros.put("producer.type", KafKaConfigUtil.KAFKA_PRODUCER_TYPE);
        //		pros.put("batch.num.messages", KafKaConfigUtil.KAFKA_BATCH_NUM_MESSAGES);
        //		pros.put("request.required.acks", KafKaConfigUtil.KAFKA_REQUEST_REQUIRED_ACKS);
        producer = new Producer<Integer, String>(new ProducerConfig(pros));
    }

    public static KafkaProducerUtil getInstance() {
        if (null == instance) {
            synchronized (KafkaProducerUtil.class) {
                if (null == instance) {
                    instance = new KafkaProducerUtil();
                }
            }
        }
        return instance;
    }

    public void send(String topic, String message) {
        try {
            if (null != message && !"".equals(message)) {
                ExecutorUtils.getES(false).execute(new Sender(topic, message));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() {
        producer.close();
    }

    /**
     * 异步发送
     *
     * @author Woo
     */
    private class Sender implements Runnable {
        private String topic;// topic
        private String message;// 自定义消息
        // construct

        public Sender(String topic, String message) {
            this.topic = topic;
            this.message = message;
        }

        @Override
        public void run() {
            if (null != producer && StringUtils.isNotBlank(topic) && StringUtils.isNotBlank(message)) {
                try {
                    producer.send(new KeyedMessage<Integer, String>(topic, message));
                    logger.info("send kafka msg:" + topic + "->" + message);
                } catch (Exception e) {
                    logger.error("send kafka msg error:", e);
                }
            } else {
                logger.error("send kafka msg error:empty parameter!  topic:{}   message:{}", topic,
                        message);
            }

        }

    }

    public static void main(String[] args) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("show_id", "show_id");
        map.put("uid", "uid");
        map.put("time", System.currentTimeMillis());
        map.put("type", "add");

        for (int i = 0; i < 5000; i++) {
            KafkaProducerUtil.getInstance().send("test", JSON.toJSONString(map));
        }
        KafkaProducerUtil.getInstance().close();
    }
}
