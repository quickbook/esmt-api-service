package com.esmt.service;

import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.esmt.dto.TokenCacheEntry;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Service
public class TokenService {
	private final SecretKey secretKey;
	private final String issuer;
	private final long accessTtlSec;
	private final long refreshTtlSec;
	private final boolean reuseSame;

	private final Cache<String, TokenCacheEntry> cache;

	public TokenService(@Value("${security.token.secret}") String secret,
			@Value("${security.token.issuer}") String issuer,
			@Value("${security.token.access-validity-seconds}") long accessTtlSec,
			@Value("${security.token.refresh-validity-seconds}") long refreshTtlSec,
			@Value("${security.token.reuse-same-access-token:true}") boolean reuseSame) {
		this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
		this.issuer = issuer;
		this.accessTtlSec = accessTtlSec;
		this.refreshTtlSec = refreshTtlSec;
		this.reuseSame = reuseSame;
		this.cache = Caffeine.newBuilder().expireAfterWrite(accessTtlSec, TimeUnit.SECONDS).maximumSize(10_000).build();
	}
	public TokenCacheEntry issueTokenWithRole(String ip, String roleName, String username) {
		return generateAndCache(ip, roleName, username);
    }

	public TokenCacheEntry issueOrReuseForIp(String ip) {
		TokenCacheEntry cached = cache.getIfPresent(ip);
		Instant now = Instant.now();
		if (reuseSame && cached != null && cached.accessExpiry().isAfter(now)) {
			return cached;
		}
		return generateAndCache(ip, "USER", null);
	}

	public TokenCacheEntry refresh(String ip, String refreshToken) {
		TokenCacheEntry cached = cache.getIfPresent(ip);
		Instant now = Instant.now();
		if (cached != null && cached.refreshToken().equals(refreshToken) && cached.accessExpiry().isAfter(now)) {
			return cached;
		}
		Jws<Claims> claims = parse(refreshToken);
		String roleName = claims.getBody().get("role", String.class); // Extract role from claims
		String username = claims.getBody().get("username", String.class);
		if (!ip.equals(claims.getBody().get("ip", String.class))) {
			throw new JwtException("IP mismatch");
		}
		if (claims.getBody().getExpiration().before(new Date())) {
			throw new JwtException("Refresh token expired");
		}
		return generateAndCache(ip, roleName, username);
	}

	public Jws<Claims> parse(String token) throws JwtException {
		return Jwts.parserBuilder().setSigningKey(secretKey).requireIssuer(issuer).build().parseClaimsJws(token);
	}

	private TokenCacheEntry generateAndCache(String ip, String roleName, String username) {
		Instant now = Instant.now();
		Instant accessExp = now.plusSeconds(accessTtlSec);
		Instant refreshExp = now.plusSeconds(refreshTtlSec);

		Map<String, Object> accessClaims = new HashMap<>();
		accessClaims.put("ip", ip);
		accessClaims.put("scope", "bootstrap");
		accessClaims.put("role", roleName);
		if (username != null && !username.isBlank()) {
			accessClaims.put("username", username);
		}

		String access = Jwts.builder().setIssuer(issuer).setSubject("ip-access")
				.addClaims(accessClaims)
				.setIssuedAt(Date.from(now))
				.setExpiration(Date.from(accessExp))
				.signWith(secretKey, SignatureAlgorithm.HS256).compact();

		Map<String, Object> refreshClaims = new HashMap<>();
		refreshClaims.put("ip", ip);
		refreshClaims.put("role", roleName);
		if (username != null && !username.isBlank()) {
			refreshClaims.put("username", username);
		}

		String refresh = Jwts.builder().setIssuer(issuer).setSubject("ip-refresh")
				.addClaims(refreshClaims)
				.setIssuedAt(Date.from(now)).setExpiration(Date.from(refreshExp))
				.signWith(secretKey, SignatureAlgorithm.HS256).compact();

		TokenCacheEntry entry = new TokenCacheEntry(access, refresh, accessExp, refreshExp);
		cache.put(ip, entry);
		return entry;
	}
}