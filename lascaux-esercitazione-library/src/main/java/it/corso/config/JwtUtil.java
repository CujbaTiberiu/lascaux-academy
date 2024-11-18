package it.corso.config;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.micrometer.common.util.StringUtils;
import it.corso.Models.UserDto;

@Component
public class JwtUtil {
	private String SECRET_KEY = "S3s1Df4F12aqQ02GRE12P2DWasvfeaQ2edaqfjfasnwnfnw7fg1g2h1b2";

	public String generateToken(String username) {
		System.out.println("generate token " + username);
		return Jwts.builder().setSubject(username).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
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
