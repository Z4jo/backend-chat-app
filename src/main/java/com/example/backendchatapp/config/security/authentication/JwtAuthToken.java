package com.example.backendchatapp.config.security.authentication;

import com.example.backendchatapp.config.security.SecurityAuthority;
import com.example.backendchatapp.entity.Authority;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;

@RequiredArgsConstructor
public class JwtAuthToken implements Authentication {
	private final String JWT;
	private boolean authenticated;
	private Collection<? extends GrantedAuthority> authorities;
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public Object getCredentials() {
		return null;
	}

	@Override
	public Object getDetails() {
		return null;
	}

	@Override
	public Object getPrincipal() {
		return null;
	}

	@Override
	public boolean isAuthenticated() {
		return this.authenticated;
	}

	@Override
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
		this.authenticated=isAuthenticated;
	}

	public String getJWT() {
		return JWT;
	}

	@Override
	public String getName() {
		return null;
	}

	public void setAuthorities(Collection<? extends GrantedAuthority> authority){
		this.authorities = authority;
	}
}
