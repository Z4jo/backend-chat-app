package com.example.backendchatapp.config.security.filter;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.backendchatapp.config.security.authentication.JwtAuthToken;
import com.example.backendchatapp.config.security.manager.JwtAuthManager;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private JwtAuthManager jwtAuthManager;
	private final String HEADER_NAME = "authorization";

	@Override
	protected void doFilterInternal(HttpServletRequest request,
	                                HttpServletResponse response,
	                                FilterChain filterChain) throws ServletException, IOException {

		if (request.getHeader(HEADER_NAME)!=null&&request.getHeader(HEADER_NAME).contains("Bearer ")) {
			String jwt = request.getHeader(HEADER_NAME).substring(7);
			var a = new JwtAuthToken(jwt);
			try{
				var finalAuth = jwtAuthManager.authenticate(a);
				SecurityContextHolder.getContext().setAuthentication(finalAuth);
				response.setStatus(HttpServletResponse.SC_ACCEPTED);
				filterChain.doFilter(request,response);
			}catch (JWTVerificationException e ){
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				filterChain.doFilter(request,response);
				e.printStackTrace();
			}
			}else {
				filterChain.doFilter(request,response);
		}
	}
}
