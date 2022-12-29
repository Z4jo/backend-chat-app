package com.example.backendchatapp.config.security.authentication;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@RequiredArgsConstructor
public class UsernamePasswordAuthToken implements Authentication {
	private boolean authenticated;
	private final String USERNAME;
    private final String PASSWORD;
	private Collection<? extends  GrantedAuthority> authorities;
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Collection<? extends  GrantedAuthority> authorities){
		this.authorities = authorities;
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

	public String getPassword() {
		return this.PASSWORD;
	}

	@Override
	public String getName() {
		return this.USERNAME;
	}
}
