package com.esmt.config;
 

import java.io.IOException;
import java.time.Instant;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.esmt.model.ApiLog;
import com.esmt.repository.ApiLogRepository;
import com.esmt.util.CommonUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class ApiLogFilter extends OncePerRequestFilter {

    private final ApiLogRepository repository;

    public ApiLogFilter(ApiLogRepository repository) {
        this.repository = repository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        long start = System.currentTimeMillis();

        try {
            filterChain.doFilter(request, response);
        } finally {
            long duration = System.currentTimeMillis() - start;

            String clientId = request.getHeader("X-Client-Id");
            String ip = CommonUtil.clientIp(request);

            ApiLog log = ApiLog.builder()
                    .clientId(clientId)
                    .ipAddress(ip)
                    .httpMethod(request.getMethod())
                    .endpoint(request.getRequestURI())
                    .statusCode(response.getStatus())
                    .durationMs(duration)
                    .timestamp(Instant.now())
                    .build();

            repository.save(log);
        }
    }

    
}
