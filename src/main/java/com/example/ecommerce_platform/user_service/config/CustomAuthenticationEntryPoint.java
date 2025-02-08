package com.example.ecommerce_platform.user_service.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.ServletException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");

        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("status", HttpServletResponse.SC_UNAUTHORIZED);
        responseMap.put("error", "Unauthorized");
        responseMap.put("message", authException.getMessage());
        responseMap.put("path", request.getRequestURI());

        response.getOutputStream().println(objectMapper.writeValueAsString(responseMap));
    }
}
