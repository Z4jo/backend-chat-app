package com.example.backendchatapp.service;

import com.example.backendchatapp.entity.User;
import com.example.backendchatapp.repositary.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
	UserRepository uRepo;

	public HttpStatus createUser(User user){
		uRepo.save(user);
		return HttpStatus.CREATED;
	}

	public ArrayList<User> getUsers() {
		return	(ArrayList<User>) uRepo.findAll();
	}

}
