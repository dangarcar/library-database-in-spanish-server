package es.library.databaseserver.security;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

@Component
public class JWTUtils {
	
	@Value("${jwt.expiration_time}")
	private Long expirationTimeMillis;
	@Value("${jwt.issuer}")
	private String issuer;
	
	private Algorithm algorithm;
	
	@Autowired
	public JWTUtils(@Value("${jwt.secret}") String secret) {
		this.algorithm = Algorithm.HMAC256(secret);
	}
	
	public String generateTokenFromUser(User user) {
		return JWT.create()
				.withSubject(user.getUsername())
				.withIssuer(issuer)
				.withIssuedAt(Instant.now())
				.withExpiresAt(Instant.now().plusMillis(expirationTimeMillis))
				.withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
				.sign(algorithm);
				
	}
	
	public String validateTokenAndGetUsername(String token) throws JWTVerificationException {
		return JWT.require(algorithm)
					.withIssuer(issuer)
					.build()
					.verify(token)
					.getSubject();
		
	}
	
}

