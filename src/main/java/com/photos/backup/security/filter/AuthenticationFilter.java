package com.photos.backup.security.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.photos.backup.constants.SecurityConstants;
import com.photos.backup.dto.AuthenticationDTO;
import com.photos.backup.dto.ErrorDTO;
import com.photos.backup.dto.ResponseDTO;
import com.photos.backup.exception.InvalidParamsException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Date;

@AllArgsConstructor
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    AuthenticationManager manager;
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
       try{
           AuthenticationDTO authenticationDTO = new ObjectMapper().readValue(request.getInputStream(),AuthenticationDTO.class);
           Authentication credentials =new UsernamePasswordAuthenticationToken(authenticationDTO.email(),authenticationDTO.password());
           return manager.authenticate(credentials);
       }catch (IOException e){
           throw new InvalidParamsException();
       }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException, ServletException {
        String  token = JWT.create()
                .withSubject(authResult.getName())
                .withExpiresAt(new Date(System.currentTimeMillis()+ SecurityConstants.TOKEN_EXPIRATION))
                .sign(Algorithm.HMAC512(SecurityConstants.SECRET_KEY));
        response.setHeader(SecurityConstants.AUTHORIZATION,SecurityConstants.BEARER + token);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed)
            throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setHeader(SecurityConstants.CONTENT_TYPE, SecurityConstants.RESPONSE_CONTENT_TYPE);
        ErrorDTO errorDTO = ErrorDTO.builder().message(failed.getMessage()).error(failed.getMessage()).errorCode(401).build();
        ResponseDTO<Object> responseDTO = ResponseDTO.builder()
                .error(errorDTO)
                .result(null)
                .build();
        response.getWriter().write(responseDTO.toJson());
        response.getWriter().flush();
    }

    @Override
    public void setFilterProcessesUrl(String filterProcessesUrl) {
        super.setFilterProcessesUrl(SecurityConstants.LOGIN_PATH);
    }
}
