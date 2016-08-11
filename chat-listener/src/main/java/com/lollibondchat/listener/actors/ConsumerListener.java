package com.lollibondchat.listener.actors;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.lollibondchat.listener.data.Message;

/**
 * @author Kaunain Wajeeh
 */

@Component
@Scope("prototype")
public class ConsumerListener implements Callable<Message> {

	private String clientId;
	private String topic;
	private KafkaConsumer<String, String> consumer;
	private final AtomicBoolean closed = new AtomicBoolean(false);

	public ConsumerListener() {

	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	/*
	 * private static void printMessages(ByteBufferMessageSet messageSet) throws
	 * UnsupportedEncodingException { for (MessageAndOffset messageAndOffset :
	 * messageSet) { ByteBuffer payload = messageAndOffset.message().payload();
	 * byte[] bytes = new byte[payload.limit()]; payload.get(bytes);
	 * System.out.println(new String(bytes, "UTF-8")); } }
	 */

	@Override
	public Message call() throws UnsupportedEncodingException {
		Message message = new Message();
		Properties properties = new Properties();
		properties.put("bootstrap.servers", "localhost:9092");
		 properties.put("acks", "all");
         properties.put("retries", 0);
         properties.put("batch.size", 16384);
         properties.put("linger.ms", 0);
         properties.put("buffer.memory", 33554432);
         properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
         properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
         properties.put("block.on.buffer.full",true);
         properties.put("group.id", "lollichat");
		consumer=new KafkaConsumer<>(properties);
		try {
			consumer.subscribe(Arrays.asList("4321"));
			while (!closed.get()) {
				ConsumerRecords<String, String> records = consumer.poll(1000l);
				for (ConsumerRecord<String, String> record : records) {
					System.out.println(record.key());
					System.out.println(record.value());
				}
			}

		} finally {
			consumer.close();
		}

		return message;

	}

}