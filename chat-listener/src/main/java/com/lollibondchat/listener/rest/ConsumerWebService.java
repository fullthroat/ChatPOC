package com.lollibondchat.listener.rest;

import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lollibondchat.listener.data.Message;

@RestController
@RequestMapping("/consumer")
public class ConsumerWebService {

/*	@Autowired
	private ListenerManager listenerManager;
	@Autowired
	private ConsumerListener consumerListener;*/

	@RequestMapping(value = "/getMessage", method = RequestMethod.POST)
	public CompletableFuture<Message> consumeChatMessage(@RequestBody String userId) throws JsonProcessingException {
		
	CompletableFuture<Message> cf = CompletableFuture.supplyAsync(new Supplier<Message>() {
			@Override
			public Message get() {
				Message message = new Message();
				Properties properties = new Properties();
				properties.put("bootstrap.servers", "localhost:9092");
				 properties.put("acks", "all");
		         properties.put("retries", 0);
		         properties.put("batch.size", 16384);
		         properties.put("buffer.memory", 33554432);
		         properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		         properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		         properties.put("block.on.buffer.full",true);
		         properties.put("group.id", "lolligrasp");
		     	final KafkaConsumer<String, String> consumer=new KafkaConsumer<>(properties);
				try {
					consumer.subscribe(Arrays.asList(userId));
					while (true) {
						ConsumerRecords<String, String> records = consumer.poll(1000);
						if(records == null || records.count() == 0)
							continue;
						for (ConsumerRecord<String, String> record : records) {
							message.setTopic(userId);
							message.setMessage(record.value());
						}
						return message;
					}
				} 
				catch(Exception e){
					e.printStackTrace();
				}
				finally {
					
					consumer.close();
					
				}
				return message;

			}
		});
		return cf;
/*		return new Callable<Message>() {
			@Override
			public Message call() throws Exception {
				consumerListener.setTopic(userId);
				Message msg=listenerManager.addThread(consumerListener);
				System.out.println(msg.getMessage());
				return msg;
			}

		};*/

	}

}
