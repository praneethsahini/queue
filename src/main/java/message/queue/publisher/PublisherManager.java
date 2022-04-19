package message.queue.publisher;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.json.simple.JSONObject;

import message.queue.consumer.ConsumerManager;

public class PublisherManager {

	private static PublisherManager managerInstance = null;
	private static Map<String, ArrayList<JSONObject>> allQueues = null;
	private final static Logger logger = Logger.getLogger(PublisherManager.class.getName());
	
	private PublisherManager()
    {
		logger.info("Initiating Message Queues");
    }
 

    public static PublisherManager getInstance()
    {
        if (managerInstance == null) {
        	managerInstance = new PublisherManager();
        	allQueues = new HashMap<>();
        }
        	
 
        return managerInstance;
    }
	
    public ArrayList<JSONObject> getQueue(String name) {
    	return allQueues.getOrDefault(name, null);
    }
	
    public void addQueue(String name) {
    	if(!hasTopic(name)) allQueues.put(name, new ArrayList<>());
    }
    
    public boolean hasTopic(String name) {
    	return allQueues.containsKey(name);
    }
    
    public void publishData(String topicName, String data) {
    	
    	if(!hasTopic(topicName)) {
    		logger.warning(topicName + "does not exist");
    		return;
    	}
    	ArrayList<JSONObject> queue = allQueues.get(topicName);
    	JSONObject dataObj = new JSONObject();
    	dataObj.put("data", data);
    	dataObj.put("timestamp", System.currentTimeMillis());
    	queue.add(dataObj);
    	ConsumerManager.getInstance().notifySubscribers(topicName);
    	
    }
	
}
