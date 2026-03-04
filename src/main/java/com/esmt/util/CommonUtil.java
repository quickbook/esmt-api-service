package com.esmt.util;

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

}
