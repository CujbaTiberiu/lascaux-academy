package it.corso.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.micrometer.common.util.StringUtils;
import it.corso.Entities.User;
import it.corso.Repositories.User_repo;
import it.corso.Services.User_service;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {
	
	@Autowired
	private JwtUtil jwt;
	@Autowired
	private User_service userService;
	

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		  // Ottieni l'URI della richiesta
	    String requestURI = request.getRequestURI();

	    // Se l'URI corrisponde a un endpoint pubblico, salta il filtro
	    if (requestURI.startsWith("/auth")) {
	        filterChain.doFilter(request, response);
	        return;
	    }

		String token = getToken(request.getHeader("Authorization"));
    	System.out.println("filter " + token);

		String username = jwt.extractUsername(token);
    	System.out.println("filter " + username);

		if (StringUtils.isNotEmpty(username) && jwt.validateToken(token)) {
			UserDetails user = userService.loadUserByUsername(username);

			UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null,
					user.getAuthorities());

			SecurityContextHolder.getContext().setAuthentication(authentication);
		}

		filterChain.doFilter(request, response);
	}
	
	public String getToken(String token) {
		if (StringUtils.isNotEmpty(token) && token.contains("Bearer")) {
			String[] tokenArr = token.split(" ");
			System.out.println(tokenArr[1]);
			return tokenArr[1];
		}
		return null;
	}

}
