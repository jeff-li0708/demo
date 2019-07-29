package kafka.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: chenggui.huang
 * @date: 2016-01-28 11:00
 */
@Component
public class MessageHandler implements InitializingBean {
	private Logger logger = LoggerFactory.getLogger(MessageHandler.class);

	@Resource
    ApplicationContext applicationContext;

	Collection<TopicConsumer> consumerList = Collections.emptyList();
	ConcurrentHashMap<String,List<TopicConsumer>> topicConsumerMap = new ConcurrentHashMap<>();

	@Override
	public void afterPropertiesSet() throws Exception {
		Map<String, TopicConsumer> consumerMap = applicationContext.getBeansOfType(TopicConsumer.class);
		consumerList = consumerMap.values();
	}

	public void handle(String topic,final int partition, final List messageList,final Integer reTryId){
		List<TopicConsumer> resolvedConsumer = resolveConsumer(topic);
		for (final TopicConsumer topicConsumer : resolvedConsumer) {
			List<byte[]> byteList = (List<byte[]>) messageList;
			topicConsumer.consume(partition, byteList, reTryId);
		}
	}

	private List<TopicConsumer> resolveConsumer(String topic){
		if (topicConsumerMap.contains(topic)) {
			return topicConsumerMap.get(topic);
		}
		List<TopicConsumer> list = new ArrayList<>();
		for (TopicConsumer consumer : consumerList) {
			if (consumer.isSupport(topic)) {
				list.add(consumer);
			}
		}
		topicConsumerMap.put(topic,list);
		return list;
	}

	public void release() {
//		if ( es != null ) {
//			es.shutdown();
//			logger.info("-------------kafka消息执行线程池关闭--------------");
//		}
	}
}
