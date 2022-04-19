package message.queue;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import message.queue.consumer.ConsumerManager;
import message.queue.publisher.PublisherManager;

public class RunnerTest {

	@Test
    public void testMain() {    
		String[] args = {"input/commands.txt"};
		Runner.main(args);
		PublisherManager publisherManager = PublisherManager.getInstance();
		ConsumerManager consumerManager = ConsumerManager.getInstance();
		assertNotNull(PublisherManager.getInstance());
		assertEquals(publisherManager.getQueue("abc").size(), 4);
		assertEquals(consumerManager.getConsumerOffset("consumer-1"), 3);
    }
	
	
}
