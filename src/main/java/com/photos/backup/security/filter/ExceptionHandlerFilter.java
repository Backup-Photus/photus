package com.photos.backup.security.filter;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.photos.backup.constants.SecurityConstants;
import com.photos.backup.dto.ErrorDTO;
import com.photos.backup.exception.ApplicationException;
import com.photos.backup.exception.UsersException;
import jakarta.annotation.Nonnull;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class ExceptionHandlerFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull FilterChain filterChain)
            throws ServletException, IOException {
        try{
            filterChain.doFilter(request,response);
        }catch (ApplicationException exception){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setHeader(SecurityConstants.CONTENT_TYPE,SecurityConstants.RESPONSE_CONTENT_TYPE);
            response.getWriter().write(exception.toErrorResponse().toResponseDTO().toJson());
            response.getWriter().flush();
        }
        catch (EntityNotFoundException entityNotFoundException){
            response.setHeader(SecurityConstants.CONTENT_TYPE,SecurityConstants.RESPONSE_CONTENT_TYPE);
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            ErrorDTO errorDTO = ErrorDTO.builder()
                    .message(UsersException.UserExceptions.USER_NOT_FOUND.name())
                    .errorCode(HttpServletResponse.SC_NOT_FOUND)
                    .build();
            response.getWriter().write(errorDTO.toResponseDTO().toJson());
            response.getWriter().flush();
        } catch (JWTVerificationException exception){
            response.setHeader(SecurityConstants.CONTENT_TYPE,SecurityConstants.RESPONSE_CONTENT_TYPE);
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            ErrorDTO errorDTO = ErrorDTO.builder()
                    .message(UsersException.UserExceptions.INVALID_CREDENTIALS.name())
                    .errorCode(HttpServletResponse.SC_FORBIDDEN)
                    .build();
            response.getWriter().write(errorDTO.toResponseDTO().toJson());
            response.getWriter().flush();
        } catch (RuntimeException exception){
            response.setHeader(SecurityConstants.CONTENT_TYPE,SecurityConstants.RESPONSE_CONTENT_TYPE);
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            ErrorDTO errorDTO = ErrorDTO.builder()
                    .message(HttpStatus.BAD_REQUEST.name())
                    .errorCode(HttpServletResponse.SC_BAD_REQUEST)
                    .build();
            response.getWriter().write(errorDTO.toResponseDTO().toJson());
            response.getWriter().flush();
        } catch (Exception exception){
            response.setHeader(SecurityConstants.CONTENT_TYPE,SecurityConstants.RESPONSE_CONTENT_TYPE);
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            ErrorDTO errorDTO = ErrorDTO.builder()
                    .message(HttpStatus.FORBIDDEN.name())
                    .errorCode(HttpServletResponse.SC_FORBIDDEN)
                    .build();
            response.getWriter().write(errorDTO.toResponseDTO().toJson());
            response.getWriter().flush();
        }
    }
}
