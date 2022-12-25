package com.example.backendchatapp.controller;

import com.example.backendchatapp.entity.User;
import com.example.backendchatapp.repositary.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

@RestController
@AllArgsConstructor
public class TestController {


UserRepository ur;
@Autowired
RabbitAdmin rabbitAdmin;
@PostMapping("/create/users")
	public void createUsers(){

	}
}

