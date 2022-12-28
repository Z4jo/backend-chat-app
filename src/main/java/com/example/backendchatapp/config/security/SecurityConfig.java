package com.example.backendchatapp.config.security;

import com.example.backendchatapp.config.security.filter.JwtAuthenticationFilter;
import com.example.backendchatapp.config.security.filter.UsernamePasswordFilter;
import com.example.backendchatapp.config.security.manager.JwtAuthManager;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@AllArgsConstructor
public class SecurityConfig {
	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	private final UsernamePasswordFilter usernamePasswordFilter;
	private final JwtAuthManager jwtAuthManager;
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
		return http
				.csrf().disable()
				.addFilterAt(usernamePasswordFilter,UsernamePasswordAuthenticationFilter.class)
				.addFilterAfter(jwtAuthenticationFilter,UsernamePasswordAuthenticationFilter.class)
				.authorizeRequests().mvcMatchers("/*/public/**").permitAll()
				.and()
				.authorizeRequests().mvcMatchers("/*/private/**").hasAuthority("USER")
				.mvcMatchers("/**").hasAuthority("ADMIN").anyRequest().authenticated()
				.and()
				.httpBasic()
				.and()
				.build();
	}


	@Bean
	public PasswordEncoder passwordEncoder(){
		return NoOpPasswordEncoder.getInstance();
	}
}
