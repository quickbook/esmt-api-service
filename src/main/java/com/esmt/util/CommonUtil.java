package com.esmt.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.esmt.security.AuthPrincipal;

import jakarta.servlet.http.HttpServletRequest;

public class CommonUtil {

	private CommonUtil() {
	}

	public static String clientIp(HttpServletRequest req) {
		String xff = req.getHeader("X-Forwarded-For");
		if (xff != null && !xff.isBlank())
			return xff.split(",")[0].trim();
		return req.getRemoteAddr();
	}

	/**
	 * Returns the current username from SecurityContext if present, otherwise null.
	 */
	public static String getCurrentUsername() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null || !auth.isAuthenticated()) return null;
		Object principal = auth.getPrincipal();
		if (principal == null) return null;
		if (principal instanceof UserDetails) {
			return ((UserDetails) principal).getUsername();
		}
		if (principal instanceof AuthPrincipal) {
			return ((AuthPrincipal) principal).getUsername();
		}
		return principal.toString();
	}

}
