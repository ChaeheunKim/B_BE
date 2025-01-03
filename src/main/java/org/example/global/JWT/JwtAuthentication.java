package org.example.global.JWT;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Component
public class JwtAuthentication implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        String exception = (String) request.getAttribute("exception");

        if (exception == null) {
            setResponse(response, "로그인이 필요합니다.");
        } else if (exception.equals("유효하지 않은 JWT 토큰 서명입니다.")) {
            setResponse(response, "유효하지 않은 JWT 토큰 서명입니다.");
        } else if (exception.equals("손상된 JWT 토큰입니다.")) {
            setResponse(response, "손상된 JWT 토큰입니다.");
        } else if (exception.equals("만료된 JWT 토큰입니다.")) {
            setResponse(response, "만료된 JWT 토큰입니다.");
        } else if (exception.equals("지원하지 않는 JWT 토큰입니다.")) {
            setResponse(response, "지원하지 않는 JWT 토큰입니다.");
        }
    }

    private void setResponse(HttpServletResponse response, String message) throws IOException {
        response.setContentType("application/json; charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        ObjectMapper om = new ObjectMapper();
        String responseBody = om.writeValueAsString(ResponseEntity.badRequest());
        response.getWriter().println(responseBody);
    }
}
