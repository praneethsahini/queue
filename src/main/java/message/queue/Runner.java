package message.queue;

import java.io.File;
import java.nio.file.Files;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import message.queue.consumer.ConsumerManager;
import message.queue.publisher.PublisherManager;


public class Runner 
{
	private final static Logger logger = Logger.getLogger(Runner.class.getName());
	
    public static void main( String[] args )
    {
    	try (Stream<String> fileLines = Files.lines(new File(args[0]).toPath())) {
    		List<String> lines = fileLines.map(String::trim).filter(s -> !s.matches(" ")).collect(Collectors.toList());
    		for (String line : lines) {
                String[] instructions = line.trim().split(" ");
                CommandEnum command = CommandEnum.valueOf(instructions[0]);
                // TO BE EXTENDED TO RUN ASYNC FOR DIFFERENT TOPICS
                switch (command) {
                    case CREATE_QUEUE:
                    	if(instructions.length>=2) 
                    		PublisherManager.getInstance().addQueue(instructions[1]);
                    	else 
                    		logger.info("Could not add queue. Please mention name.");            	
                        break;
                    case PUBLISH:
                    	if(instructions.length>=3) {
                    		String topic = instructions[1];
                        	String data = instructions[2];
                        	PublisherManager.getInstance().publishData(topic, data);
                    	}
                    	else {
                    		logger.info("Publish in format: PUBLISH <topic-name> <data>");
                    	}
                    	
                        break;
                    case CREATE_SUBSCRIBER:
                    	if(instructions.length>=3) {
                    		String topicName = instructions[1];
                        	String consumerGroup = instructions[2];
                        	ConsumerManager.getInstance().subscribeTopic(topicName, consumerGroup);
                    	}
                    	else {
                    		logger.info("Subscribe in format: CREATE_SUBSCRIBER <topicName> <consumer-group>");
                    	}
                    	
                        break;
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
