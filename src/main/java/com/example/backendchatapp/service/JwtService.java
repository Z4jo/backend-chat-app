package com.example.backendchatapp.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
public class JwtService {

	public String encoder(Long id, String username) {
		try {
			ZonedDateTime expiration = ZonedDateTime.now().plusMinutes(15);
			Algorithm algo = Algorithm.HMAC512("too_secret_too_handle");
			String token = JWT.create()
					.withSubject(id.toString())
					.withSubject(username)
					.withExpiresAt(expiration.toInstant())
					.sign(algo);
			return token;
		} catch (JWTVerificationException e) {
			System.out.println("jwt creation failed "+ e.getStackTrace());
		}
		return null;
	}
	public DecodedJWT decoder(String token){
		try {
			Algorithm algo = Algorithm.HMAC512("too_secret_too_handle");
			JWTVerifier verifier = JWT.require(algo)
					.build();
			return verifier.verify(token);
		}catch (JWTVerificationException e) {
			System.out.println("failed to verify jwt token "+ e.getStackTrace());
		}
		return null;
		}
	}



