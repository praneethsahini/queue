package message.queue.consumer;

import org.junit.jupiter.api.Test;

import message.queue.publisher.PublisherManager;

import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

public class ConsumerManagerTest {

    @Test
    public void testConsumerManagerExists() {
        assertNotNull(ConsumerManager.getInstance());
    }
	
	@Test
    public void testTopicSubscribe() {
		PublisherManager publisherManager = PublisherManager.getInstance();
		publisherManager.addQueue("myqueue-1");
		ConsumerManager consumerManager = ConsumerManager.getInstance();
		consumerManager.subscribeTopic("myqueue-1", "consumer-2");
		Map<String, ArrayList<String>> allQueues = consumerManager.getAllQueues();
		Map<String, Integer> consumerOffsets = consumerManager.getConsumerOffsets();
		
        assertNotNull(allQueues);
        assertNotNull(consumerOffsets);
        System.out.println(allQueues + " - " +consumerOffsets);
        assertEquals(allQueues.get("myqueue-1").get(0), "consumer-2");
        assertEquals(consumerOffsets.get("consumer-2"), -1);
        
        
    }
	
}
