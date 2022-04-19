package message.queue.consumer;

import java.util.logging.Logger;

public class ConsumerAction {
	private final static Logger logger = Logger.getLogger(ConsumerAction.class.getName());

	static void printObjects(Object obj, String topicName, String consumerGroup) {
		logger.info("Topic: "+topicName + " ,Consumer Group: "+consumerGroup + " ,Data: "+ obj);
	}
}
