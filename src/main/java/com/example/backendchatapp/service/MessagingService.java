package com.example.backendchatapp.service;

import com.example.backendchatapp.entity.*;
import com.example.backendchatapp.repositary.ChatRepository;
import com.example.backendchatapp.repositary.MediaMessageRepository;
import com.example.backendchatapp.repositary.TextMessageRepository;
import com.example.backendchatapp.repositary.UserRepository;
import lombok.AllArgsConstructor;
import org.apache.catalina.core.ApplicationContext;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.DirectMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Service
@AllArgsConstructor
public class MessagingService {
	RabbitAdmin ra;
	RabbitTemplate rt;
	ChatRepository chRepo;
	UserRepository uRepo;
	MediaMessageRepository mediaRepo;
	TextMessageRepository textRepo;
	DirectMessageListenerContainer directMessageListenerContainer;
	public HttpStatus sendMessage(Long chatId, String message, List<Long> consumers, Long publisher) throws EntityNotFoundException {

		//get entities
		User user = uRepo.findById(publisher).orElseThrow(EntityNotFoundException::new);
		ZonedDateTime now = ZonedDateTime.now();
		TextMessage textMessage = new TextMessage();
		textMessage.setMessage(message);
		textMessage.setDate(Timestamp.valueOf(now.toLocalDateTime()));
		textMessage.setUser(user);
		Chat chat = chRepo.findChatByUsers(user);
		chat.getTextMessages().add(textMessage);
		textMessage.setChat(chat);
		//send to queue
		var exchange = new DirectExchange("exchange." + chatId);
		createQueuesFromIds(consumers, chatId,exchange);
		ArrayList<CustomMessage> messages= createMessages(consumers,message);
		for (CustomMessage msg :messages){
			rt.convertAndSend(exchange.getName(),"",msg);
		}
		//save entities
		chRepo.save(chat);
		return HttpStatus.ACCEPTED;
	}

	private ArrayList<CustomMessage> createMessages(List<Long> consumers, String message) {
		ArrayList<CustomMessage>ret= new ArrayList<>();
		for(Long id : consumers){
			ret.add(new CustomMessage(message,null,id,"text/message"));
		}
		return ret;
	}


	public Message getMessagesFromQueue(Long chatId, Long userId) {
		return rt.receive("queue." + userId + "." + chatId);
	}

	public void getChatHistory(Long chatId) {
		//Todo:sorting by date
	}

	public HttpStatus sendImage(byte[] messageBytes, List<Long> consumers, Long publisher, Long chatId) throws EntityNotFoundException {
		//get entities
		MediaMessage mm = new MediaMessage();
		User user = uRepo.findById(publisher).orElseThrow(EntityNotFoundException::new);
		Chat chat = chRepo.findChatByUsers(user);
		ZonedDateTime now = ZonedDateTime.now();
		mm.setBlobData(messageBytes);
		mm.setUser(user);
		mm.setChat(chat);
		mm.setDate(Timestamp.valueOf(now.toLocalDateTime()));
		chat.getMediaMessages().add(mm);
		//send to queue
		DirectExchange exchange = new DirectExchange("exchange." + chatId);
		createQueuesFromIds(consumers, chatId,exchange);

		MessageProperties msgP = new MessageProperties();
		msgP.setContentType("image");
		Message msg = new Message(messageBytes, msgP);
		rt.send(exchange.getName(), "", msg);
		//save entities
		chRepo.save(chat);
		return HttpStatus.ACCEPTED;
	}

	public HttpStatus chatInitialization(Collection<User> userList) {
		Chat chat = new Chat();
		for (User user : userList) {
			chat.getUsers().add(user);
		}
		chRepo.save(chat);
		FanoutExchange exchange = new FanoutExchange("exchange." + chat.getId());
		ra.declareExchange(exchange);
		return HttpStatus.CREATED;
	}


	public void createQueuesFromIds(List<Long> ids, Long chatId, DirectExchange exchange) {
		for (Long id : ids) {
			var q = new Queue("queue." + id + "." + chatId);
			var binding= BindingBuilder.bind(q).to(exchange).with(q.getName());
			ra.declareQueue(q);
			ra.declareBinding(binding);
			directMessageListenerContainer.addQueues(q);
		}
	}

}



















