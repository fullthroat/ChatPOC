package launcher;

import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

/**
 * @author Kaunain Wajeeh
 */


public class ConsumerListener extends Thread {

	private String clientId;
	private String topic;
	private KafkaConsumer<String, String> consumer;
	private final AtomicBoolean closed = new AtomicBoolean(false);
	
	public static void main(String args[]){
		ConsumerListener listener=new ConsumerListener();
		listener.start();
	}

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
	public void run(){
		
		Properties consumerProperties = new Properties();
		consumerProperties.put("bootstrap.servers", "localhost:9092");
		 consumerProperties.put("acks", "all");
         consumerProperties.put("retries", 0);
         consumerProperties.put("batch.size", 16384);
         consumerProperties.put("linger.ms", 0);
         consumerProperties.put("buffer.memory", 33554432);
         consumerProperties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
         consumerProperties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
         consumerProperties.put("block.on.buffer.full",true);
         consumerProperties.put("group.id", "lulichat");
		consumer=new KafkaConsumer<>(consumerProperties);
		try {
			consumer.subscribe(Arrays.asList("user82"));
			while (!closed.get()) {
				ConsumerRecords<String, String> records = consumer.poll(1000);
				for (ConsumerRecord<String, String> record : records) {
					System.out.println(record.key());
					System.out.println(record.value());
				}
			}

		} finally {
			consumer.close();
		}

		

	}

}
