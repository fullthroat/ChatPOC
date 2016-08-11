package com.cassandraexp.main;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

@SpringBootApplication
public class CassandraContactApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(CassandraContactApplication.class, args);

		String serverIp = "127.0.0.1";
	    String keyspace = "pigeon";
	    Cluster cluster = Cluster.builder()
	            .addContactPoints(serverIp).withPort(9042)
	            .build();

	    Session session = cluster.connect(keyspace);


	    String cqlStatement = "Select * from message_thread_121";
	    for (Row row : session.execute(cqlStatement)) {
	        System.out.println(row.toString());
	    }
		
	}

	@Override
	public void run(String... arg0) throws Exception {
		// TODO Auto-generated method stub
		
	}
	

	
		
}
