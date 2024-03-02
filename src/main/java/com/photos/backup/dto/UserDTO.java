package com.photos.backup.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.photos.backup.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;

@Builder
public record UserDTO(
        @NotBlank(message = "Username cannot be blank")
        String username,

        @NotBlank(message = "Name cannot be null")
        String name,

        @NotBlank(message = "Email cannot be blank")
        @Email
        String email,

        @JsonIgnore
        @NotBlank(message = "Password cannot be blank")
        String password,

        String avatarUrl
) {
   static public UserDTO fromUser(User user){
       return UserDTO.builder()
               .password(user.getPassword())
               .username(user.getUsername())
               .avatarUrl(user.getAvatarUrl())
               .email(user.getEmail())
               .name(user.getName())
               .build();
   }
   public User toUser(){
       return User.builder()
               .name(name)
               .password(password)
               .username(username)
               .email(email)
               .avatarUrl(avatarUrl)
               .build();
   }

}
