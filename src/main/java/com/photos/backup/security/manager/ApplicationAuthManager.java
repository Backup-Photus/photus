package com.photos.backup.security.manager;

import com.photos.backup.constants.AuthConstants;
import com.photos.backup.entity.User;
import com.photos.backup.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


@AllArgsConstructor
@Component
public class ApplicationAuthManager implements AuthenticationManager {

    private UserService userService;
    private PasswordEncoder passwordEncoder;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        User user = userService.getFromEmail(authentication.getName());
         if(!passwordEncoder.matches(authentication.getCredentials().toString(), user.getPassword())){
             throw new BadCredentialsException(AuthConstants.WRONG_PASSWORD);
         }
         return new UsernamePasswordAuthenticationToken(user.getId(),user.getEmail());
    }
}
