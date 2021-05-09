package com.example.Apartment.Util;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


/**
 * @author ARUN VEMIREDDY
 *
 */
@Component
public class JwtUtil {
	
	@Value("${app.secret}")
	private String secret;

	//Generate Token
	public String generatetoken(String subject) {
		
		return Jwts.builder().setSubject(subject)
				.setIssuer("arun")
			    .setIssuedAt(new Date(System.currentTimeMillis()))
			    .setExpiration(new Date(System.currentTimeMillis()+TimeUnit.MINUTES.toMillis(120)))
			    .signWith(SignatureAlgorithm.HS512, secret.getBytes())
			    .compact();
	}
	
	//get Claims body
	public Claims getClaims(String token) {
		return Jwts.parser().setSigningKey(secret.getBytes())
				.parseClaimsJws(token)
				.getBody();
	}
	
	
	//get Expiry Date
	public Date getExpDate(String token) {
		return getClaims(token).getExpiration();
	}
	
	//get UserName from token
     public String getUsername(String token) {
		return getClaims(token).getSubject();
	}
     
   //validation Expiry Date
 	public boolean isTokenExp(String token) {
 		Date expDate = getExpDate(token);
 		return expDate.before(new Date(System.currentTimeMillis()));
 	}
 	
 	//validate user name in token and database ,expDate
    public boolean validateToken(String token,String username) {
 		String tokenUsername=getUsername(token);
 		return (username.equals(tokenUsername)&&!isTokenExp(token));
 	}
     
}
