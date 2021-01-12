package com.launchcode.sunrise_meditation_java.config;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cglib.core.internal.Function;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Clock;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClock;

/**
 * 
 * Util class is to create and validate JWT token
 *
 */
@Component
public class JwtTokenUtil implements Serializable
{
	
	public static final long JWT_TOKEN_VALIDITY = 1000 * 3600;
	
	private Clock clock = DefaultClock.INSTANCE;
	 
	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.token.expiration.in.seconds}")
	private Long expiration;
	
	// retrieve username from jwt token
	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	// retrieve expiration date from jwt token
	public Date getExpirationDateFromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	// for retrieveing any information from token we will need the secret key
	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(secret)
		.parseClaimsJws(token).getBody();
	}

	// check if the token has expired
	private Boolean isTokenExpired(String token) {
		final Date expiration = getExpirationDateFromToken(token);
		return expiration.before(new Date());
	}

	private Boolean ignoreTokenExpiration(String token) {
	    // here you specify tokens, for that the expiration is ignored
	    return false;
	  }
	
	// generate token for user
	public String generateToken(UserDetails userDetails) {
		Map<String, Object> claims = new HashMap<>();
		String username = userDetails.getUsername();
		return doGenerateToken(claims, username);
	}

	// while creating the token -
	// 1. Define claims of the token, like Issuer, Expiration, Subject, and the ID
	// 2. Sign the JWT using the HS512 algorithm and secret key.
	// 3. According to JWS Compact
	// compaction of the JWT to a URL-safe string
	private String doGenerateToken(Map<String, Object> claims, String subject) {
		return Jwts.builder().setClaims(claims).setSubject(subject)
			.setIssuedAt(new Date(System.currentTimeMillis()))
			.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY))
			.signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	// validate token
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = getUsernameFromToken(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
	
	public Boolean canTokenBeRefreshed(String token) {
	    return (!isTokenExpired(token) || ignoreTokenExpiration(token));
	  }
	
	public String refreshToken(String token) {
	    final Date createdDate = clock.now();
	    final Date expirationDate = calculateExpirationDate(createdDate);

	    final Claims claims = getAllClaimsFromToken(token);
	    claims.setIssuedAt(createdDate);
	    claims.setExpiration(expirationDate);

	    return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, secret).compact();
	  }
	
	private Date calculateExpirationDate(Date createdDate) {
	    return new Date(createdDate.getTime() + expiration * 1000);
	  }
}