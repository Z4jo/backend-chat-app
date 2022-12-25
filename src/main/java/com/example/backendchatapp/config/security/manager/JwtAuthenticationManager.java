package com.example.backendchatapp.config.security.manager;

import com.example.backendchatapp.config.security.provider.JwtProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class JwtAuthenticationManager implements AuthenticationManager {
	private final JwtProvider provider;
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		if(provider.supports(authentication.getClass())){
			return provider.authenticate(authentication);
		}
		return authentication;
	}
}
