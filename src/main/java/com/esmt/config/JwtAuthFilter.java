package com.esmt.config;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.esmt.dto.ErrorDetails;
import com.esmt.response.dto.ApiResponse;
import com.esmt.security.AuthPrincipal;
import com.esmt.service.ExceptionLoggingService;
import com.esmt.service.TokenService;
import com.esmt.util.CommonUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
	
	private final TokenService tokenService;
	private final ObjectMapper objectMapper;
	private final ExceptionLoggingService exceptionLogger;

	public JwtAuthFilter(TokenService tokenService, ObjectMapper objectMapper, ExceptionLoggingService exceptionLogger) {
		this.tokenService = tokenService;
		this.objectMapper = objectMapper;
		this.exceptionLogger = exceptionLogger;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		   if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
	            // important: do not try to authenticate OPTIONS requests
	            filterChain.doFilter(request, response);
	            return;
	        }
		   
		String auth = request.getHeader(HttpHeaders.AUTHORIZATION);
		if (auth != null && auth.startsWith("Bearer ")) {
			String token = auth.substring(7);
			try {
				Jws<Claims> jws = tokenService.parse(token);
				String ipInToken = jws.getBody().get("ip", String.class);
				String roleName = jws.getBody().get("role", String.class);
                if (roleName == null) {
                    roleName = "USER"; // Default to USER if role is missing
                }
				String clientIp = CommonUtil.clientIp(request);
				// try to read username claim if present
				String username = jws.getBody().get("username", String.class);
				if (username == null || username.isBlank()) {
					username = jws.getBody().getSubject();
					if ("ip-access".equalsIgnoreCase(username) || "ip-refresh".equalsIgnoreCase(username)) {
						username = null;
					}
				}
				// store both username and ip in a small principal object
				if (clientIp != null && clientIp.startsWith("::ffff:"))
					clientIp = clientIp.substring(7);
				if (!clientIp.equals(ipInToken)) {
					throw new JwtException("IP mismatch");
				}
				// Use the extracted role to create the authority list
                List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + roleName.toUpperCase()));
				final String principalUsername = username;
				
				Authentication authentication = new AbstractAuthenticationToken(authorities) {
					@Override
					public Object getCredentials() {
						return token;
					}

					@Override
					public Object getPrincipal() {
						return new AuthPrincipal(principalUsername, ipInToken);
					}
				};
				((AbstractAuthenticationToken) authentication).setAuthenticated(true);
				SecurityContextHolder.getContext().setAuthentication(authentication);
			} catch (JwtException ex) {
				// Invalid token -> let Security chain handle 401/403 later
				
				exceptionLogger.save(request, ex, HttpServletResponse.SC_UNAUTHORIZED, CommonUtil.clientIp(request));
				ErrorDetails details = new ErrorDetails("TOKEN_INVALID", ex.getMessage(), null, ex.getClass().getSimpleName());
				ApiResponse<Object> apiResponse = ApiResponse.<Object>builder()
						.success(false)
						.message("JWT Token is invalid or expired")
						.data(null)
						.errorDetails(details)
						.status(HttpStatus.UNAUTHORIZED)
						.path(request.getRequestURI())
						.timestamp(System.currentTimeMillis())
						.build();
				response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				response.setContentType(MediaType.APPLICATION_JSON_VALUE);
				response.getWriter().write(objectMapper.writeValueAsString(apiResponse));
				
				return;
			}
		}
		filterChain.doFilter(request, response);
	}

}