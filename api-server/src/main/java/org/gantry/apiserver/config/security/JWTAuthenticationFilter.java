package org.gantry.apiserver.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.gantry.apiserver.domain.User;
import org.gantry.apiserver.web.dto.ErrorResponse;
import org.gantry.apiserver.web.dto.UserTokenResponse;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.io.OutputStream;

import static org.gantry.apiserver.config.security.JWTProperties.AUTHZ_HEADER;
import static org.gantry.apiserver.config.security.JWTProperties.BEARER_PREFIX;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final ObjectMapper objectMapper;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil, ObjectMapper objectMapper) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.objectMapper = objectMapper;
        setFilterProcessesUrl("/auth/token");
    }

    @SneakyThrows(IOException.class)
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        User user = objectMapper.readValue(request.getInputStream(), User.class);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword(), user.getAuthorities());
        return authenticationManager.authenticate(token);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        User principal = (User) authResult.getPrincipal();
        String token =  jwtUtil.create(principal);
        response.addHeader(AUTHZ_HEADER, BEARER_PREFIX + token);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        OutputStream responseStream = response.getOutputStream();
        UserTokenResponse dto = UserTokenResponse.from(principal);
        dto.setAccessToken(token);
        objectMapper.writeValue(responseStream, dto);
        responseStream.flush();
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        exception.printStackTrace();
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        OutputStream responseStream = response.getOutputStream();
        objectMapper.writeValue(responseStream, ErrorResponse.builder()
                .uri(request.getRequestURI())
                .status(UNAUTHORIZED)
                .message("Authentication Failed")
                .detail(exception.getMessage())
                .build());
        responseStream.flush();
    }
}
