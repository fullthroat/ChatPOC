package com.lollibondchat.publisher;

import java.util.Properties;
import java.util.Random;

import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.ZkConnection;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Component;

/*import kafka.admin.AdminUtils;
import kafka.common.TopicExistsException;
import kafka.utils.ZKStringSerializer$;
import kafka.utils.ZkUtils;*/

@Component
public class ChatMessagePublisher {

	private KafkaProducer<String, String> producer;


	public ChatMessagePublisher() {
		
		
	}

	public boolean publishChatMessage(String message, String userId) {
		
		Properties props = new Properties();
		props.put("bootstrap.servers", "localhost:9092");
		props.put("acks", "all");
		props.put("retries", 0);
		props.put("batch.size", 16384);
		props.put("linger.ms", 0);
		props.put("buffer.memory", 33554432);
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		producer = new KafkaProducer<String, String>(props);

/*		String zookeeperConnect = "localhost:2181";
		boolean isSecureKafkaCluster = false;
	    int partitions = 1;
	    int replication = 1;*/
	    		
		/*ZkClient zkClient = new ZkClient("localhost:2181", 10000, 10000, ZKStringSerializer$.MODULE$);
		ZkUtils zkUtils = new ZkUtils(zkClient, new ZkConnection(zookeeperConnect), isSecureKafkaCluster);
		try{
			AdminUtils.createTopic(zkUtils, userId, partitions, replication, new Properties(),null);
		}
		catch(TopicExistsException ex){
			System.out.println("Topic Exists");
		}*/
		
		ProducerRecord<String, String> data = new ProducerRecord<String, String>(userId, message);
		try {
			producer.send(data);
			producer.close();
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}

	}

}
