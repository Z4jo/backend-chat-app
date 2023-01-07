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
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
@Controller
public class WebsocketController {

	@Autowired
	SimpMessagingTemplate template;

	@PostMapping("/message/public/send/{chatId}")
	public ResponseEntity<Void> sendMessage(@RequestBody Message message, @PathVariable int chatId) {
		template.convertAndSend("/topic/message/"+chatId, message);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@MessageMapping("/message/public/sendMessage/{chatId}")
	@SendTo("/topic/message/{chatId}")
	public GenericMessage<String> receiveMessage( @DestinationVariable int chatId, @Payload GenericMessage<String> message) {
		System.out.println("messageMapping");
		System.out.println(message.getPayload());
		return message;
	}
}
