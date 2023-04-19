package org.gantry.apiserver.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.gantry.apiserver.domain.User;
import org.gantry.apiserver.web.dto.ErrorResponse;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.io.OutputStream;

import static org.gantry.apiserver.config.security.JWTProperties.AUTHZ_HEADER;
import static org.gantry.apiserver.config.security.JWTProperties.BEARER_PREFIX;
import static org.springframework.http.HttpStatus.FORBIDDEN;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private final JWTUtil jwtUtil;
    private final ObjectMapper objectMapper;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil, ObjectMapper objectMapper) {
        super(authenticationManager);
        this.jwtUtil = jwtUtil;
        this.objectMapper = objectMapper;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = getTokenFrom(request);
        if (jwtUtil.verify(token)) {
            User user = jwtUtil.decode(token);
            Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
    }

    private String getTokenFrom(HttpServletRequest request) {
        String authHeader = request.getHeader(AUTHZ_HEADER);
        if (authHeader == null || !authHeader.startsWith(BEARER_PREFIX)) {
            return null;
        }
        return  authHeader.substring(BEARER_PREFIX.length());
    }



    @Override
    protected void onUnsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException {
        exception.printStackTrace();
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        OutputStream responseStream = response.getOutputStream();
        objectMapper.writeValue(responseStream, ErrorResponse.builder()
                .uri(request.getRequestURI())
                .status(FORBIDDEN)
                .message("Access Denied")
                .detail(exception.getMessage())
                .build());
        responseStream.flush();
    }
}

