package com.bondinco.socketload;

import org.json.JSONObject;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class Launcher {

	public static void main(String args[]) {

		System.out.println("client started");
		
		for (int i = 0; i < 1000000; i++) {
			if(i%100000==0){System.out.println(i);}
			JSONObject obj = new JSONObject();
			obj.put("username", i);
			obj.put("sesionid", i);
			obj.put("initialmessage", "I am " + i);
			/*
			 * IO.Options opts = new IO.Options(); opts.forceNew = true;
			 * opts.reconnection = false;
			 */
			try {
				Socket socket = IO.socket("http://localhost:5054");
				socket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {

					@Override
					public void call(Object... args) {
						socket.emit("register", obj);
						
					}

				}).on("event", new Emitter.Listener() {

					@Override
					public void call(Object... args) {
					}

				}).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {

					@Override
					public void call(Object... args) {
					}

				});
				socket.connect();
				break;

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
