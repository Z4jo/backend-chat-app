package com.example.backendchatapp.config.security;

import com.example.backendchatapp.config.security.filter.JwtAuthenticationFilter;
import com.example.backendchatapp.config.security.filter.UsernamePasswordFilter;
import com.example.backendchatapp.config.security.manager.JwtAuthManager;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.Arrays;
import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@AllArgsConstructor
public class SecurityConfig  {
	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	private final UsernamePasswordFilter usernamePasswordFilter;
	private final JwtAuthManager jwtAuthManager;
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
		return http
				.csrf().disable()
				.addFilterAt(usernamePasswordFilter,UsernamePasswordAuthenticationFilter.class)
				.addFilterAfter(jwtAuthenticationFilter,UsernamePasswordAuthenticationFilter.class)
				.authorizeRequests().mvcMatchers(HttpMethod.OPTIONS,"/**").permitAll()
				.and()
				.authorizeRequests().mvcMatchers("/*/public/**").permitAll()
				.and()
				.authorizeRequests()
				.mvcMatchers("/*/private/**").hasAnyAuthority("USER","ADMIN")
				.mvcMatchers("/*/admin/**").hasAuthority("ADMIN").anyRequest().authenticated()
				.and()
				.httpBasic()
				.and()
				.build();
	}
	@Bean
	public CorsWebFilter corsFilter() {
		CorsConfiguration config = new CorsConfiguration();

		config.setAllowCredentials(true);
		config.addAllowedMethod("*");
		config.addAllowedHeader("*");
		config.addAllowedOrigin("http://localhost:3000");

		var source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**",config);

		return new CorsWebFilter(source);
	}



	@Bean
	public PasswordEncoder passwordEncoder(){
		return NoOpPasswordEncoder.getInstance();
	}
}
