package com.example.backendchatapp.controller;

import com.example.backendchatapp.config.security.SecurityUser;
import com.example.backendchatapp.entity.User;
import com.example.backendchatapp.repositary.UserRepository;
import com.example.backendchatapp.service.JpaUserDetailsService;
import com.example.backendchatapp.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/user")
public class UserController {
	private UserService ur;
	private JpaUserDetailsService jpaUserDetailsService;


	@PostMapping("/register")
	public HttpStatus registerUser(@RequestBody User user) {
		System.out.println(user.toString());
		return ur.createUser(user);
	}

	@GetMapping("/login")
	public String getUserLogin() {
		return "OK";
	}
	@GetMapping("/get/users/by/{substring}")
	public List<UserRepository.NamesAndId> getUsersBySubstring(@PathVariable String substring){
		return ur.getUsersBySubstring(substring);
	}
}