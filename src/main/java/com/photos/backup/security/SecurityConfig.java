package com.photos.backup.security;

import com.photos.backup.constants.SecurityConstants;
import com.photos.backup.security.filter.AuthenticationFilter;
import com.photos.backup.security.filter.ExceptionHandlerFilter;
import com.photos.backup.security.filter.JWTAuthenticationFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@AllArgsConstructor
public class SecurityConfig {

    AuthenticationManager authenticationManager;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        AuthenticationFilter authenticationFilter=new AuthenticationFilter(authenticationManager);
        ExceptionHandlerFilter exceptionHandlerFilter = new ExceptionHandlerFilter();
        JWTAuthenticationFilter jwtAuthenticationFilter = new JWTAuthenticationFilter();
        httpSecurity.
                csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request->request
                        .requestMatchers(SecurityConstants.REGISTER_PATH).permitAll()
                                .anyRequest().authenticated())
                .sessionManagement(session->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(exceptionHandlerFilter,AuthenticationFilter.class)
                .addFilter(authenticationFilter)
                .addFilterAfter(jwtAuthenticationFilter,AuthenticationFilter.class);


    return httpSecurity.build();
    }
}
