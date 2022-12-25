package com.example.backendchatapp.service;

import com.example.backendchatapp.repositary.UserRepository;
import com.example.backendchatapp.config.security.SecurityUser;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public SecurityUser loadUserByUsername(String username) {
		var u = userRepository.findByUserName(username);
		return u.map(SecurityUser::new)
				.orElseThrow(() -> new UsernameNotFoundException("Username not found " + username));
	}
}
