package com.lollibondchat.listener.actors;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.stereotype.Component;

import com.lollibondchat.listener.data.Message;

@Component
public class ListenerManager {

	private ExecutorService executor = Executors.newFixedThreadPool(2);

	public Message addThread(Callable<Message> callable) {
		Message message=null;
		try {
			 message= executor.submit(callable).get();
			 return message;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return message;
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return message;
		}
	}

	public void killAllThreads() {
		if (!executor.isShutdown()) {
			executor.shutdown();
		}
	}

}
