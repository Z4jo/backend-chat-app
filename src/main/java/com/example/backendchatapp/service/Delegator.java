package com.example.backendchatapp.service;

import com.example.backendchatapp.config.WebsocketConfig;
import com.example.backendchatapp.entity.CustomMessage;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.socket.messaging.WebSocketStompClient;

@Component
@AllArgsConstructor
public class Delegator  {
	@Autowired
	SimpMessagingTemplate template;
	private final String URL = "http://localhost:8080/test/public/ws-message";

	public void receiver(CustomMessage message){
		template.convertAndSend("/topic/message/"+message.id(), message);
	}

}
