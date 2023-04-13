package org.gantry.apiserver.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.gantry.apiserver.web.dto.ErrorResponse;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.io.OutputStream;

import static jakarta.servlet.http.HttpServletResponse.SC_FORBIDDEN;
import static jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;


@Profile("!simple-security")
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.addAllowedOriginPattern("*");
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder passwordEncoder,
                                                       UserDetailsService userDetailsService) throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder)
                .and()
                .build();
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint(ObjectMapper objectMapper) {
        return (request, response, exception) -> {
            exception.printStackTrace();
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);

            OutputStream responseStream = response.getOutputStream();
            objectMapper.writeValue(responseStream, ErrorResponse.builder()
                    .uri(request.getRequestURI())
                    .status(UNAUTHORIZED)
                    .message("Authentication Failed")
                    .detail(exception.getMessage())
                    .build());
            responseStream.flush();
        };
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler(ObjectMapper objectMapper) {
        return (request, response, exception) -> {
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
        };
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                .requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**", "/health", "/ws/**")
                .requestMatchers(PathRequest.toH2Console())
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authenticationManager,
                                           JWTUtil jwtUtil, ObjectMapper objectMapper) throws Exception {
        JWTAuthenticationFilter jwtAuthnFilter = new JWTAuthenticationFilter(authenticationManager, jwtUtil, objectMapper);
        JWTAuthorizationFilter jwtAuthzFilter = new JWTAuthorizationFilter(authenticationManager, jwtUtil, objectMapper);

        http.csrf().disable()
            .httpBasic().disable()
            .formLogin().disable()
            .addFilter(jwtAuthnFilter)
            .addFilter(jwtAuthzFilter)
            .cors()
                .and()
            .authorizeHttpRequests()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/v1/users").permitAll()
                .requestMatchers("/api/v1/users/**").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/applications").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/v1/applications/*").permitAll()
                .requestMatchers("/api/v1/platforms").hasAnyRole("ADMIN")
                .requestMatchers("/api/v1/platforms/**").hasAnyRole("ADMIN")
                .requestMatchers("/api/v1/**").hasAnyRole("ADMIN", "USER")
                .requestMatchers("/ws/**").permitAll()
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers("/health").permitAll()
                .anyRequest().authenticated()
                .and()
//            .exceptionHandling()
//                .authenticationEntryPoint(authenticationEntryPoint(objectMapper))
//                .accessDeniedHandler(accessDeniedHandler(objectMapper))
//                .and()
            .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        ;

        return http.build();
    }

}