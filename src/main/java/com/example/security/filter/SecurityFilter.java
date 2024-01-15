package com.example.security.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.Apartment.Util.JwtUtil;

import io.jsonwebtoken.SignatureException;

/**
 * @author ARUN VEMIREDDY
 *
 */
@Component
public class SecurityFilter extends OncePerRequestFilter implements WebMvcConfigurer {

	public final static Logger log = LogManager.getLogger(SecurityFilter.class);

	private JwtUtil util;

	private UserDetailsService userDetailsService;

	@Autowired
	public SecurityFilter(JwtUtil util, UserDetailsService userDetailsService) {
		super();
		this.util = util;
		this.userDetailsService = userDetailsService;
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedOrigins("*").allowedMethods("GET", "POST", "PUT", "DELETE");
	}

	private void readCookies(Cookie[] cookies) {
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				String name = cookie.getName();
				String value = cookie.getValue();
				log.info("Cookie Name: " + name + ", Value: " + value);
			}
		}
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// Check if cookies are present
		Cookie[] cookies = request.getCookies();
		readCookies(cookies);
		String token = request.getHeader("Authorization");
		if (token != null) {
			String username = util.getUsername(token);
			if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				UserDetails user = userDetailsService.loadUserByUsername(username);
				boolean isValid = false;
				try {
					isValid = util.validateToken(token, user.getUsername());
				} catch (SignatureException e) {
					log.error(e.getMessage());
				}
				if (isValid) {
					UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
							username, user.getPassword(), user.getAuthorities());
					authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
					SecurityContextHolder.getContext().setAuthentication(authenticationToken);
				}
			}
		}
		filterChain.doFilter(request, response);
	}
}
