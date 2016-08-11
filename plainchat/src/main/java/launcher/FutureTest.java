package launcher;

import java.util.Arrays;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

public class FutureTest {
	public static void main(String[] args) throws InterruptedException {
		

		Properties props = new Properties();
		props.put("bootstrap.servers", "localhost:9092");
		props.put("acks", "all");
		props.put("retries", "0");
		props.put("batch.size", 16384);
		props.put("linger.ms", 0);
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		KafkaProducer<String, String> producer = new KafkaProducer<String, String>(props);
		
		Properties properties2 = new Properties();
		properties2.put("bootstrap.servers", "localhost:9092");
		properties2.put("group.id", "hazems");
		properties2.put("enable.auto.commit", true);
		properties2.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		properties2.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

		properties2.put("auto.commit.interval.ms", 1000);
        properties2.put("session.timeout.ms", 30000);

		
		Executor myexec = Executors.newFixedThreadPool(100, new ThreadFactory() {
			@Override
			public Thread newThread(Runnable r) {
		     	final KafkaConsumer<String, String> consumer=new KafkaConsumer<>(properties2);
				return new KafkaConsumerThread(consumer);
			}
		});
		for(int i = 0; i < 100 ; i++) {
			myexec.execute(new Runnable() {
				@Override
				public void run() {
				}
			});
		}
		
		while(true) {

			publishMessage(producer);
			Thread.sleep(1000);
		}
	}
	
	private static class KafkaConsumerThread extends Thread {
		KafkaConsumer<String, String> consumer;
		
		public KafkaConsumerThread(KafkaConsumer<String, String> consumer) {
			System.out.println("creating thread");
			this.consumer = consumer;
		}
		
		@Override
		public void run() {try {
			consumer.subscribe(Arrays.asList("hazem"));
			while(true) {
				ConsumerRecords<String, String> records = consumer.poll(1000000);
				for (ConsumerRecord<String, String> record : records) {
					System.out.println(record.value());
				}
				}
			} 
			catch(Exception e){
				e.printStackTrace();
			}
			finally {
				//consumer.close();
			}
		}
	}
	
	private static void publishMessage(KafkaProducer<String, String> producer) {
		
		ProducerRecord<String, String> data = new ProducerRecord<String, String>("hazem", new Date().toString());
		try {
			producer.send(data);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
}
