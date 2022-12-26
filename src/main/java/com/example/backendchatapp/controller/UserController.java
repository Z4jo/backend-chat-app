package com.example.backendchatapp.controller;

import com.example.backendchatapp.config.security.SecurityUser;
import com.example.backendchatapp.entity.User;
import com.example.backendchatapp.service.JpaUserDetailsService;
import com.example.backendchatapp.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@AllArgsConstructor
public class UserController {
	private UserService ur;
	private JpaUserDetailsService jpaUserDetailsService;


	@PostMapping("/register/user")
	public HttpStatus registerUser(@RequestBody User user) {
		System.out.println(user.toString());
		return ur.createUser(user);
	}

	@GetMapping("/get/users")
	public ArrayList<User> getUsers() {
		return ur.getUsers();
	}

	@GetMapping("/get/user/{name}")
	public SecurityUser getUser(@PathVariable String name) {
		return jpaUserDetailsService.loadUserByUsername(name);
	}

	@GetMapping("/get/user/login")
	public String getUserLogin() {
		return "OK";
	}
}