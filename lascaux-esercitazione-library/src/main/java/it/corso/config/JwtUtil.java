package it.corso.config;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.micrometer.common.util.StringUtils;
import it.corso.Models.UserDto;

@Component
public class JwtUtil {
	
	@Value("${app.jwt-secret}")
	private String SECRET_KEY;
	
	@Value("${app.jwt-expiration-milliseconds}")
	private long jwtExpirationDate;

	public String generateToken(String username) {
		System.out.println("generate token " + username);
		return Jwts.builder().setSubject(username).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + jwtExpirationDate)) // 10 hours
				.signWith(key()).compact();
		
	}

	private SecretKey key() {
		// decode the Base64-encoded secret and use it to create the signing key
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
	}

	public String extractUsername(String token) {
		return Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token).getBody().getSubject();
	}

//	public boolean validateToken(String token, String username) {
//		return extractUsername(token).equals(username);
//	}
	public boolean validateToken(String token) {
        // parse the token with the secret key an exception will be thrown if invalid
		Jwts.parserBuilder().setSigningKey(key())
		.build()
		.parse(token);
		return true;
	}

}
