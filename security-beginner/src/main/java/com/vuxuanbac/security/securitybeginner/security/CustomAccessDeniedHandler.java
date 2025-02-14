package com.vuxuanbac.security.securitybeginner.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.json.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationEntryPoint.class);

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        logger.error("Unauthorized error: {}", accessDeniedException.getMessage());

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("error", accessDeniedException.getMessage());
        body.put("message", "You dont have permission for accessing this resource");
        body.put("path", request.getRequestURI());
        String bodyStr = body.keySet().stream()
                .map(key -> "\"" + key + "\": \"" + body.get(key) + "\"")
                .collect(Collectors.joining(", ", "{", "}"));


        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        response.getWriter().write(bodyStr);
    }
}
