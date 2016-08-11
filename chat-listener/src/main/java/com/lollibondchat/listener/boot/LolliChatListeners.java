package com.lollibondchat.listener.boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.lollibondchat.listener")
public class LolliChatListeners {

	public static void main(String[] args) {
		SpringApplication.run(LolliChatListeners.class, args);
	}
		
	
}
