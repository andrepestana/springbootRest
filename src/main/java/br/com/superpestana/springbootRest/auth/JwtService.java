package br.com.superpestana.springbootRest.auth;

import java.time.Instant;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.superpestana.springbootRest.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

@Service
public class JwtService {

	@Value("${jwt.key}")
	String key;
	
	static final long TOKEN_DURATION_SECONDS = 30 * 60 * 1000;
	
	public void validateToken(HttpServletRequest req) throws ServletException {
		String token = req.getHeader("Authentication");
		if (token == null) {
			throw new ServletException("Invalid token");
		}
		try {
			Jwts.parser().setSigningKey(key).parseClaimsJws(token.substring(7)).getBody();
		} catch (SignatureException e) {
			throw new ServletException("Invalid token");
 
		}
	}
	public String generateNewToken(User user) {
		String token = "Bearer "+Jwts.builder()
				  .setSubject(user.getUsername())
				  .signWith(SignatureAlgorithm.HS512, key) 
				  .setExpiration(Date.from(Instant.now().plusSeconds(TOKEN_DURATION_SECONDS)))
				  .compact();
		return token;
	}
}
