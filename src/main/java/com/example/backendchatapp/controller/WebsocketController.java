package com.example.backendchatapp.controller;

import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Arrays;

public class WebsocketController {
	@Autowired
	SimpMessagingTemplate template;

	@PostMapping("/send/{chatId}")
	public ResponseEntity<Void> sendMessage(@RequestBody Message message, @PathVariable String chatId) {
		template.convertAndSend("/topic/message/"+chatId, message);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@MessageMapping("/sendMessage/{chatId}")
	public void receiveMessage(@DestinationVariable int chatId, @Payload Message message) {
		System.out.println("messageMapping");
		System.out.println(chatId);
		System.out.println(Arrays.toString(message.getBody()));
	}


	@SendTo("/topic/message/{chatId}")
	public Message broadcastMessage(@DestinationVariable int chatId,@Payload Message message) {
		System.out.println("testTo");
		System.out.println(chatId);
		System.out.println(Arrays.toString(message.getBody()));
		return message;
	}
}
