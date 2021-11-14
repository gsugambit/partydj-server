package com.gsugambit.partydjserver.security;

import java.lang.reflect.Field;
import java.time.Instant;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultJwtBuilder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class TokenHelper {
	private static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;

	@Value("${spring.application.name}")
	private String appName;

	@Value("${jwt.secret}")
	public String secret;

	@Value("${jwt.expires_in}")
	private int expiresIn;

	@Value("${jwt.header}")
	private String authHeader;

	public String getUsernameFromToken(String token) {
		String username;
		final Claims claims = this.getAllClaimsFromToken(token);
		username = claims.getSubject();

		return username;
	}

	public Date getIssuedAtDateFromToken(String token) {
		Date issueAt;

		final Claims claims = this.getAllClaimsFromToken(token);
		issueAt = claims.getIssuedAt();
		return issueAt;
	}

	public String getAudienceFromToken(String token) {
		String audience;
		final Claims claims = this.getAllClaimsFromToken(token);
		audience = claims.getAudience();
		return audience;
	}

	public Date getExpirationFromToken(String token) {
		Date expiration;

		final Claims claims = this.getAllClaimsFromToken(token);
		expiration = claims.getExpiration();

		return expiration;
	}

	public String refreshToken(String token) {
		String refreshedToken;
		Date now = new Date();
		final Claims claims = this.getAllClaimsFromToken(token);
		Date expiration = new Date((Integer) claims.get(Claims.EXPIRATION));

		LOGGER.trace("Token expired: {}, generating new token.", now.after(expiration));
		claims.setIssuedAt(now);
		refreshedToken = Jwts.builder().setClaims(claims).setExpiration(generateExpirationDate())
				.signWith(SIGNATURE_ALGORITHM, secret).compact();
		return refreshedToken;
	}

	public String generateToken(String username) {

		JwtBuilder builder = Jwts.builder().setIssuer(appName).setSubject(username).setAudience("web")
				.setIssuedAt(new Date()).setExpiration(generateExpirationDate()).signWith(SIGNATURE_ALGORITHM, secret);

		if (LOGGER.isDebugEnabled()) {

			Field header = ReflectionUtils.findField(DefaultJwtBuilder.class, "header");
			header.setAccessible(true);
			Field claims = ReflectionUtils.findField(DefaultJwtBuilder.class, "claims");
			claims.setAccessible(true);

			try {
				LOGGER.debug("generated jwt: \r\n{}\r\n{}", header.get(builder), claims.get(builder));
			} catch (IllegalArgumentException e) {
				LOGGER.warn(e.getMessage(), e);
			} catch (IllegalAccessException e) {
				LOGGER.warn(e.getMessage(), e);
			} finally {
				header.setAccessible(false);
				claims.setAccessible(false);
			}
		}

		return builder.compact();
	}

	private Claims getAllClaimsFromToken(String token) {
		Claims claims;
		claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();

		return claims;
	}

	private Date generateExpirationDate() {
		return new Date(Instant.now().toEpochMilli() + expiresIn * 1000);
	}

	public int getExpiredIn() {
		return expiresIn;
	}

	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = getUsernameFromToken(token);
		final Date created = getIssuedAtDateFromToken(token);
		final Date expiration = getExpirationFromToken(token);

		LOGGER.trace("exiration: {}. Right now: {}", expiration.toString(), new Date().toString());

		return (username != null && username.equals(userDetails.getUsername()) && created != null && expiration != null
				&& expiration.after(new Date()));
	}

	public String getToken(HttpServletRequest request) {
		/**
		 * Getting the token from Authentication header e.g Bearer your_token
		 */
		String authHeader = getAuthHeaderFromHeader(request);
		if (authHeader != null && authHeader.startsWith("Bearer ")) {
			return authHeader.substring(7);
		} else {
			LOGGER.warn("Request did not contain {} header", authHeader);
		}

		return null;
	}

	public String getAuthHeaderFromHeader(HttpServletRequest request) {
		return request.getHeader(authHeader);
	}
}
