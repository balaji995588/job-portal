package com.jobportal.configuration;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil {

	@Value("${JwtSecret}")
	private String secret;

	private static final long JWT_TOKEN_VALIDITY_FOR_ACCESS_TOKEN = 604800;

	private static final long JWT_TOKEN_VALIDITY_FOR_REFRESH_TOKEN = 604800;

	public String getUsernameFromToken(String token) {
		return getClaimFromToken(token, Claims::getSubject);
	}

	public String getUserIdFromToken(String token) {
		return getClaimFromToken(token, Claims::getId);
	}

	public Date getExpirationDatefromToken(String token) {
		return getClaimFromToken(token, Claims::getExpiration);
	}

	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = getAllClaimsFromToken(token);
		return claimsResolver.apply(claims);
	}

	private Claims getAllClaimsFromToken(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();

	}

	private Boolean isAccessTokenExpired(String token) {
		final Date expirationDate = getExpirationDatefromToken(token);
		return expirationDate.before(new Date());
	}

	public String getTokenType(String token) {
		final Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		return (String) claims.get("type");
	}

	public String generateAccessToken(UserDetails userDetails) {

		Map<String, Object> claims = new HashMap<String, Object>();

		return doGenerateAccessToken(claims, userDetails);

	}

	private String doGenerateAccessToken(Map<String, Object> claims, UserDetails userDetails) {

		String token = Jwts.builder().setClaims(claims).claim("type", "access").setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY_FOR_ACCESS_TOKEN * 1000))
				.signWith(SignatureAlgorithm.HS512, secret).compact();

		return token;

	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = getUsernameFromToken(token);
		return username.equals(userDetails.getUsername()) && !isAccessTokenExpired(token);
	}

	public String generateRefreshToken(String accessToken, UserDetails userDetails) {

		final Date createdDate = new Date(System.currentTimeMillis());
		final Date expirationDate = new Date(createdDate.getTime() + (JWT_TOKEN_VALIDITY_FOR_REFRESH_TOKEN * 1000));

		final Claims claims = getAllClaimsFromToken(accessToken);
		claims.setIssuedAt(createdDate);
		claims.setExpiration(expirationDate);

		String subject = userDetails.getUsername();
		return Jwts.builder().setClaims(claims).claim("type", "refresh").setSubject(subject)
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}

	public boolean canTokenBeRefreshed(String token) {
		return (!isAccessTokenExpired(token) || ignoreTokenExpiration(token));
	}

	private boolean ignoreTokenExpiration(String token) {
		return false;
	}
}
