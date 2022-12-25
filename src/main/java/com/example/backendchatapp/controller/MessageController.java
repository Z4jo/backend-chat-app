package com.example.backendchatapp.controller;

import com.example.backendchatapp.entity.User;
import com.example.backendchatapp.service.MessagingService;
import lombok.AllArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/message")
public class MessageController {
	MessagingService ms;

//Post
	@PostMapping(value = "/send/{chatId}/{consumer}/{publisher}")
	public HttpStatus sendTextMessage(@PathVariable Long chatId,@PathVariable Long consumer,@PathVariable Long publisher,
	                              @RequestBody String message){
		return ms.sendMessage(chatId,message,consumer,publisher);

	}

	@PostMapping("send/image/{chatId}/{reciever}/{sender}")
	public HttpStatus sendImage(@RequestParam("file")MultipartFile file, @PathVariable Long chatId,
	                            @PathVariable Long reciever, @PathVariable Long sender) throws IOException {
		return ms.sendImage(file.getBytes(),reciever,sender,chatId);
	}
	@PostMapping("/chat/initialization")
	public HttpStatus chatInitialization(@RequestBody Collection<User> userList){
		for (User user: userList){
			System.out.println(user.getUserName());
		}
		return ms.chatInitialization(userList);
	}

//Get
	@GetMapping(value = "/get/{chatId}/{consumer}")
	public Message getMessage(@PathVariable Long chatId, @PathVariable Long consumer){
		return ms.getMessage(chatId,consumer);
	}

	@GetMapping(value = "/get/chat/history/{chatId}")
	public void getChatHistory(@PathVariable Long chatId){

	}



}
