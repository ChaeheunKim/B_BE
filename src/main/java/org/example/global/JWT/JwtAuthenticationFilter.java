package org.example.global.JWT;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final PrincipalUserDetailService principalUserDetailService;

    @Autowired
    public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider ,PrincipalUserDetailService principalUserDetailService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.principalUserDetailService= principalUserDetailService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        String token = getTokenFromRequest(request);
        if (token != null && !token.isBlank()) {
            try {
                String subject = jwtTokenProvider.getSubjectFromToken(token);// 토큰에서 subject 추출 및 만료 시간 검증
                UserDetails user = principalUserDetailService.loadUserByUsername(subject);

                // 사용자 존재 여부 확인 후 인증 객체 생성 및 SecurityContext에 저장
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (SignatureException e) {
                request.setAttribute("exception", "유효하지 않은 JWT 토큰 서명입니다.");
            } catch (MalformedJwtException e) {
                request.setAttribute("exception", "손상된 JWT 토큰입니다.");
            } catch (ExpiredJwtException e) {
                request.setAttribute("exception", "만료된 JWT 토큰입니다.");
            } catch (UnsupportedJwtException e) {
                request.setAttribute("exception", "지원하지 않는 JWT 토큰입니다.");
            } catch (IllegalArgumentException e) {
                request.setAttribute("excepiton","JWT 토큰 내에 정보가 없습니다.");
            }
        }

        filterChain.doFilter(request, response);
    }


    private String getTokenFromRequest(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(HttpHeaders.AUTHORIZATION))
                .filter(authHeader -> authHeader.startsWith("Bearer "))
                .map(authHeader -> authHeader.substring(7))
                .orElse(null);
    }
}
