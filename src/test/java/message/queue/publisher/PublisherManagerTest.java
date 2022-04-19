package message.queue.publisher;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Map;

import org.json.simple.JSONObject;
import org.junit.jupiter.api.Test;

import message.queue.consumer.ConsumerManager;

public class PublisherManagerTest {

    @Test
    public void testConsumerManagerExists() {
        assertNotNull(PublisherManager.getInstance());
    }
    
    @Test
    public void testAddQueue() {
    	PublisherManager.getInstance().addQueue("myqueue");
    	assertTrue(PublisherManager.getInstance().hasTopic("myqueue"));
    }
	
	@Test
    public void testPublishData() {
		PublisherManager publisherManager = PublisherManager.getInstance();
		publisherManager.addQueue("myqueue2");
		publisherManager.publishData("myqueue2", "mydata");
		ArrayList<JSONObject> queue = publisherManager.getQueue("myqueue2");
		
        assertNotNull(queue);
        System.out.println(queue );
        assertEquals(queue.size(), 1);
        assertEquals(queue.get(0).get("data"), "mydata");
        
        
    }
	
}
