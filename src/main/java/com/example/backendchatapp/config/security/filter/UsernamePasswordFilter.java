package com.example.backendchatapp.config.security.filter;

import com.example.backendchatapp.config.security.authentication.UsernamePasswordAuthToken;
import com.example.backendchatapp.config.security.manager.UsernamePasswordAuthManager;
import com.example.backendchatapp.service.JpaUserDetailsService;
import com.example.backendchatapp.service.JwtService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Base64;
@Component
@AllArgsConstructor
public class UsernamePasswordFilter extends OncePerRequestFilter {

	private UsernamePasswordAuthManager usernamePasswordAuthManager;
	private JpaUserDetailsService jpaUserDetailsService;
	private JwtService jwtService;
	private final String HEADER_NAME = "authorization";

	@Override
	protected void doFilterInternal(HttpServletRequest request,
	                                HttpServletResponse response,
	                                FilterChain filterChain)
									throws ServletException, IOException {
			if(request.getHeader(HEADER_NAME)!=null&&request.getHeader(HEADER_NAME).contains("Basic")) {
				String[] header = request.getHeader(HEADER_NAME).split(" ");
				String[] usernamePassword = new String(Base64.getDecoder().decode(header[1])).split(":");
				var a = new UsernamePasswordAuthToken(usernamePassword[0], usernamePassword[1]);
				//TODO:EXCEPTIONS
				try {
					var finalAuthentication = usernamePasswordAuthManager.authenticate(a);
					SecurityContextHolder.getContext().setAuthentication(finalAuthentication);
					response.addHeader(HEADER_NAME, generateJwt(usernamePassword[0]));
					response.setStatus(HttpServletResponse.SC_ACCEPTED);
					filterChain.doFilter(request, response);
				} catch (BadCredentialsException e) {
					e.printStackTrace();
					response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
					filterChain.doFilter(request, response);
				}
			}else{
				filterChain.doFilter(request,response);
			}

	}
	private String generateJwt(String username){
		var u = jpaUserDetailsService.loadUserByUsername(username);
		return jwtService.encoder(u.getId(),u.getUsername());
	}
}
