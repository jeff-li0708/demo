package kafka;

import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.core.MessageHandler;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.message.MessageAndMetadata;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class KafkaHighLevelConsumer implements DisposableBean {
	private Logger logger = LoggerFactory.getLogger(getClass());

	private final ConsumerConnector consumer;
	private Properties props;
	private Map<String,Integer> topicStreamsMap;
	private Map<String,ExecutorService> topicExecutorMap = new HashedMap();

	@Resource
	MessageHandler messageHandler;


	public KafkaHighLevelConsumer(Properties props, Map<String, Integer> topicStreamsMap) {
		this.props = props;
		this.topicStreamsMap = topicStreamsMap;
		consumer = kafka.consumer.Consumer.createJavaConsumerConnector(new ConsumerConfig(this.props));
	}

	public void run() {
		logger.info("----------开启kafka消费者----------");
		try {
			Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumer.createMessageStreams(topicStreamsMap);
			for (Map.Entry<String, Integer> entry : topicStreamsMap.entrySet()) {
				String topic = entry.getKey();
				List<KafkaStream<byte[], byte[]>> streams = consumerMap.get(topic);
				ExecutorService es = Executors.newFixedThreadPool(topicStreamsMap.get(topic));

				topicExecutorMap.put(topic, es);
				for (final KafkaStream stream : streams) {
					es.submit(new Runnable() {
						@Override
						public void run() {
							ConsumerIterator<byte[], byte[]> it = stream.iterator();
							while (it.hasNext()) {
								try {
									MessageAndMetadata messageAndMetadata = it.next();
									byte[] message = (byte[]) messageAndMetadata.message();
									messageHandler.handle(messageAndMetadata.topic(), messageAndMetadata.partition(), Collections.singletonList(message), -1);
								} catch (Exception e) {
									logger.error("messageHandler.handle error", e);
								}
							}
						}
					});
				}
			}
		}catch (Exception e){
			logger.error("KafkaHighLevelConsumer run error", e);
		}
	}

	@Override
	public void destroy() throws Exception {
		logger.info("----------关闭kafka消费者----------");

		if (consumer != null) {
			consumer.shutdown();
		}
		if (topicExecutorMap != null) {
			for (ExecutorService es : topicExecutorMap.values()) {
				es.shutdown();
			}
		}
	}

}
