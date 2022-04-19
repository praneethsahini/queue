package message.queue.consumer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.json.simple.JSONObject;

import message.queue.Runner;
import message.queue.publisher.PublisherManager;


public class ConsumerManager {

	private static ConsumerManager managerInstance = null;
	private static Map<String, ArrayList<String>> allQueues = null;
	private static Map<String, Integer> consumerOffsets = null;
	private final static Logger logger = Logger.getLogger(ConsumerManager.class.getName());
	
	private ConsumerManager()
    {
		logger.info("Initiating Subscription");
    }
 

    public static ConsumerManager getInstance()
    {
        if (managerInstance == null) {
        	managerInstance = new ConsumerManager();
        	allQueues = new HashMap<>();
        	consumerOffsets = new HashMap<>();
        }
        return managerInstance;
    }

    protected Map<String, ArrayList<String>> getAllQueues(){
    	return allQueues;
    }
    
    protected Map<String, Integer> getConsumerOffsets(){
    	return consumerOffsets;
    }
    
    // can be exposed as an API later
    public int getConsumerOffset(String consumerGroup){
    	return consumerOffsets.getOrDefault(consumerGroup, -1);
    }
    
    public void subscribeTopic(String topicName, String consumerGroup) {
    	if(PublisherManager.getInstance().hasTopic(topicName)) {
    		ArrayList consumerList = allQueues.getOrDefault(topicName, new ArrayList<>());
    		consumerList.add(consumerGroup);
    		allQueues.put(topicName, consumerList);
    		consumerOffsets.put(consumerGroup, -1);
    		notifySubscribers(topicName);
    		logger.info(consumerGroup + " subscribed to "+topicName );
    	}
    }
    
    public void notifySubscribers(String topicName) {
    	ArrayList<JSONObject> queue = PublisherManager.getInstance().getQueue(topicName);
    	for(String consumers: allQueues.getOrDefault(topicName, new ArrayList<>())) {
    		int offsetForTopic = consumerOffsets.getOrDefault(consumers, -1)+1;
    		while(queue.size() > offsetForTopic) {
    			ConsumerAction.printObjects(queue.get(offsetForTopic), topicName, consumers);
    			offsetForTopic++;
    		}
    		consumerOffsets.put(consumers, offsetForTopic-1);
    	}
    }
	
}
