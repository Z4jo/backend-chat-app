package com.example.backendchatapp.service;

import com.example.backendchatapp.entity.Chat;
import com.example.backendchatapp.entity.MediaMessage;
import com.example.backendchatapp.entity.TextMessage;
import com.example.backendchatapp.entity.User;
import com.example.backendchatapp.repositary.ChatRepository;
import com.example.backendchatapp.repositary.MediaMessageRepository;
import com.example.backendchatapp.repositary.TextMessageRepository;
import com.example.backendchatapp.repositary.UserRepository;
import com.rabbitmq.client.AMQP;
import lombok.AllArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

//TODO: inicializace chatu;
@Service
@AllArgsConstructor
public class MessagingService {
	RabbitAdmin ra;
	RabbitTemplate rt;
	ChatRepository chRepo;
	UserRepository uRepo;
	MediaMessageRepository mediaRepo;
	TextMessageRepository textRepo;	
	public HttpStatus sendMessage(Long chatId, String message,Long consumer, Long publisher){
		//get entities
		User user = uRepo.findById(publisher).orElseThrow(EntityNotFoundException::new);
		TextMessage textMessage = new TextMessage();
		textMessage.setMessage(message.toString());
		Chat chat= chRepo.findChatByUsers(user);
		chat.getTextMessages().add(textMessage);
		//save entities
		chRepo.save(chat);
		textRepo.save(textMessage);
		//send to queue
		FanoutExchange exchange = new FanoutExchange("exchange."+chatId);
		Queue queue = new Queue("que."+consumer+"."+chatId);
		Binding binding = BindingBuilder.bind(queue).to(exchange);
		rt.send(exchange.getName(),"",new Message(message.getBytes(StandardCharsets.UTF_8)));

		return HttpStatus.ACCEPTED;
	}

	public Message getMessage(Long chatId, Long userId){
		return  rt.receive("que."+userId+"."+chatId);
	}

	public void getChatHistory(Long chatId){
     //Todo:sorting by date
	}

	public HttpStatus sendImage(byte[] bytes, Long consumer, Long publisher, Long chatId ) {
		//get entities
		MediaMessage mm = new MediaMessage();
		mm.setBlobData(bytes);
		User user = uRepo.findById(publisher).orElseThrow(EntityNotFoundException::new);
		mm.setUser(user);
		Chat chat= chRepo.findChatByUsers(user);
		chat.getMediaMessages().add(mm);
		//save entities
		chRepo.save(chat);
		mediaRepo.save(mm);
		//send to queue
		FanoutExchange exchange = new FanoutExchange("exchange."+chatId);
		Queue queue = new Queue("que."+consumer+"."+chatId);
		Binding binding = BindingBuilder.bind(queue).to(exchange);
		rt.send(exchange.getName(),"",new Message(bytes));

		return HttpStatus.ACCEPTED;
	}

	public HttpStatus chatInitialization(Collection<User> userList) {
		Chat chat = new Chat();
		for (User user : userList) {
			chat.getUsers().add(user);
		}
		chRepo.save(chat);
		return HttpStatus.CREATED;
	}
}
