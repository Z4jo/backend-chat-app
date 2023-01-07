package com.example.backendchatapp.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
@Configuration
public class WebConfig implements WebFluxConfigurer {
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/user/**")
				.allowedOrigins("http:localhost:3000")
				.allowedMethods("GET","POST")
				.allowedHeaders("Authorization")
				.allowCredentials(true);
		WebFluxConfigurer.super.addCorsMappings(registry);
	}
}
