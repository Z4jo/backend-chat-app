package com.example.backendchatapp.config.security;

import com.example.backendchatapp.entity.Authority;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@AllArgsConstructor
public class SecurityAuthority implements GrantedAuthority {


	private final Authority AUTHORITY;

	@Override
	public String getAuthority() {
		return this.AUTHORITY.getNameOfAuthority();
	}
}
