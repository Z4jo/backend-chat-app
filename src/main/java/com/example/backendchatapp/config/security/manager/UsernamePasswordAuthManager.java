package com.example.backendchatapp.config.security.manager;

import com.example.backendchatapp.config.security.provider.UsernamePasswordProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UsernamePasswordAuthManager implements AuthenticationManager {
	private UsernamePasswordProvider usernamePasswordProvider;
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		if (usernamePasswordProvider.supports(authentication.getClass())){
			return usernamePasswordProvider.authenticate(authentication);
		}else{
			return authentication;
		}
	}
}
