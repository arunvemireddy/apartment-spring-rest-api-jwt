package com.example.Apartment.Util;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

/**
 * @author ARUN VEMIREDDY
 *
 */
@Component
public class JwtUtil {

	@Value("${app.secret}")
	private String secret;

	private static final Logger log = LogManager.getLogger(JwtUtil.class);

	// Generate Token
	public String generatetoken(String subject) {

		return Jwts.builder().setSubject(subject).setIssuer("arun").setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(120)))
				.signWith(SignatureAlgorithm.HS512, secret.getBytes()).compact();
	}

	// get Claims body
	public Claims getClaims(String token) {
		try {
			return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
		} catch (Exception e) {
			log.error("Error parsing token claims: " + e.getMessage());
			throw new RuntimeException("Error parsing token claims", e);
		}
	}

	// get Expiry Date of token
	public Date getExpDate(String token) {
		return getClaims(token).getExpiration();
	}

	// get UserName from token
	public String getUsername(String token) throws SignatureException {
		String username = getClaims(token).getSubject();
		return username;
	}

	// validation Expiry Date
	public boolean isTokenExp(String token) {
		Date expDate = getExpDate(token);
		return expDate.before(new Date(System.currentTimeMillis()));
	}

	// validate user name in token and database ,expDate
	public boolean validateToken(String token, String username) {
		String tokenUsername = getUsername(token);
		return (username.equals(tokenUsername) && !isTokenExp(token));
	}

}
