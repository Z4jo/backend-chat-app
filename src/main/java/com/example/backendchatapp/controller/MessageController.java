package com.example.backendchatapp.controller;

import com.example.backendchatapp.entity.MessageConsumersWrapper;
import com.example.backendchatapp.entity.User;
import com.example.backendchatapp.service.MessagingService;
import lombok.AllArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/message/private")
public class MessageController {
	MessagingService ms;

//Post
	@PostMapping(value = "/send/{chatId}/{publisher}")
	public HttpStatus sendTextMessage(@PathVariable Long chatId, @PathVariable Long publisher,
	                                  @RequestBody MessageConsumersWrapper messageConsumersWrapper){
		return ms.sendMessage(chatId,messageConsumersWrapper.getMessage(),messageConsumersWrapper.getConsumers(),publisher);

	}

	@PostMapping("/send/image/{chatId}/{sender}")
	public HttpStatus sendImage(@RequestParam("file")MultipartFile file, @PathVariable Long chatId,
	                            @PathVariable Long sender,@RequestBody List<Long> receivers ) throws IOException {
		return ms.sendImage(file.getBytes(), receivers,sender,chatId);
	}
	@PostMapping("/chat/initialization")
	public HttpStatus chatInitialization(@RequestBody Collection<User> userList){
		for (User user: userList){
			System.out.println(user.getUserName());
		}
		return ms.chatInitialization(userList);
	}

//Get
//	@GetMapping(value = "/get/{chatId}/{consumer}")
//	public Message getMessage(@PathVariable Long chatId, @PathVariable Long consumer){
//		return ms.getMessagesFromQueue(chatId,consumer);
//	}

	@GetMapping(value = "/get/chat/history/{chatId}/{messageIndex}")
	public void getChatHistory(@PathVariable Long chatId, @PathVariable Long messageIndex){

	}
	@GetMapping("test")
	public MessageConsumersWrapper test(){
		MessageConsumersWrapper t = new MessageConsumersWrapper();
		t.setMessage("yo");
		ArrayList<Long>ar=new ArrayList<>();
		ar.add(1L);
		ar.add(2L);
		ar.add(3L);
		t.setConsumers(ar);
		return t;
	}
}
