package org.gantry.apiserver.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.gantry.apiserver.config.security.JWTUtil;
import org.gantry.apiserver.domain.User;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.util.Map;

import static org.gantry.apiserver.config.security.JWTProperties.AUTHZ_HEADER;
import static org.gantry.apiserver.config.security.JWTProperties.BEARER_PREFIX;

@Slf4j
@RequiredArgsConstructor
@Component
public class WebSocketAuthInterceptor extends HttpSessionHandshakeInterceptor {

  private final JWTUtil jwtUtil;

  @Override
  public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                 WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
    String token = extractBearerToken(request);
    if (token == null) {
      log.info(AUTHZ_HEADER + " token can not be extracted from the HTTP Request");
      return false;
    }

    User user = jwtUtil.decode(token);
    Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), null, user.getAuthorities());
    if (authentication == null || !authentication.isAuthenticated()) {
      log.info("Authentication was failed : " + user);
      return false;
    }

    SecurityContextHolder.getContext().setAuthentication(authentication);
    attributes.put("username", authentication.getName());
    return true;
  }

  private String extractBearerToken(ServerHttpRequest request) {
    String authHeader = request.getHeaders().getFirst(AUTHZ_HEADER);
    if (authHeader == null || !authHeader.startsWith(BEARER_PREFIX)) {
      return null;
    }
    return  authHeader.substring(BEARER_PREFIX.length());
  }

}