package com.example.backendchatapp.config.security.provider;

import com.example.backendchatapp.config.security.authentication.UsernamePasswordAuthToken;
import com.example.backendchatapp.service.JpaUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UsernamePasswordProvider implements AuthenticationProvider {
    private JpaUserDetailsService userDetailsService;
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		UsernamePasswordAuthToken usernamePasswordAuthToken = (UsernamePasswordAuthToken) authentication;
		UserDetails userDetails = userDetailsService.loadUserByUsername(usernamePasswordAuthToken.getName());
		//TODO: compare by hashing the password from user to hash in database
		if(userDetails.getPassword().equals(usernamePasswordAuthToken.getPassword())){
			usernamePasswordAuthToken.setAuthenticated(true);
			return usernamePasswordAuthToken;
		}else{
			throw new BadCredentialsException("incorrect password");
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return UsernamePasswordAuthToken.class.equals(authentication);
	}
}
