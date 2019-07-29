package kafka.consumer;


import kafka.core.TopicConsumer;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * kafka消费者样例实现，isSupport表示是否支持某一主题的消息的消费，一个class可以处理多个topic，也可以只处理一个
 *
 * @author: chenggui.huang
 * @date: 2016-01-28 11:54
 */
//@Component
public class AllTopicConsumer implements TopicConsumer {
	@Override
	public boolean isSupport(String topic) {
		return true;
	}

	@Override
	public void consume(int partition, List<byte[]> messageList,Integer retryId) {
		for (byte[] object : messageList) {
			try {
				System.out.println(new String(object,"utf-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
	}
}
