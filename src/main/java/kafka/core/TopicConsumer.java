package kafka.core;

import java.util.List;

/**
 * @author: chenggui.huang
 * @date: 2016-01-28 11:22
 */
public interface TopicConsumer {
	/**
	 * 是否需要对指定的topic进行处理
	 * @param topic
	 * @return
	 */
	boolean isSupport(String topic);

	/**
	 * 消费一次拉取返回的消息，可能是多条
	 * @param partition 本次消息所在的分区，kafka一次返回的消息在一个分区内是有序的，应用如果不关注该数据可忽略
	 * @param messageList 本次拉取到的消息内容列表，返回的是字节数组，需要各消费者自行转换成字符串或者其他类型
	 * @param retryId 重新执行的消息ID，为空标识该消息为新消息，不为空表示该消息为重试消息
	 */
	void consume(int partition, List<byte[]> messageList, Integer retryId);
}
