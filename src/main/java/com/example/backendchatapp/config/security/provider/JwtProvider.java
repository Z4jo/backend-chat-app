package com.example.backendchatapp.config.security.provider;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.backendchatapp.config.security.SecurityUser;
import com.example.backendchatapp.entity.User;
import com.example.backendchatapp.service.JpaUserDetailsService;
import com.example.backendchatapp.service.JwtService;
import com.example.backendchatapp.config.security.authentication.JwtAuthToken;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Component
@RequiredArgsConstructor
public class JwtProvider implements AuthenticationProvider {

	private final JwtService jwtService;
	private final JpaUserDetailsService jpaUserDetailsService;


	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		JwtAuthToken auth = (JwtAuthToken) authentication;
		DecodedJWT decodedToken = jwtService.decoder(auth.getJWT());
		ZonedDateTime timeNow = ZonedDateTime.now();
		SecurityUser user =jpaUserDetailsService.loadUserByUsername(decodedToken.getSubject());
		if (decodedToken.getExpiresAtAsInstant().isAfter(timeNow.toInstant())){
			auth.setAuthorities(user.getAuthorities());
			auth.setAuthenticated(true);
			return auth;
		}else {
			throw new JWTVerificationException("Token expired.");
		}


	}

	@Override
	public boolean supports(Class<?> authentication) {
		return JwtAuthToken.class.equals(authentication);
	}
}
