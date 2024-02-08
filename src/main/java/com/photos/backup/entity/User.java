package com.photos.backup.entity;


import com.photos.backup.constants.DatabaseConstants;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = DatabaseConstants.USERS_DATABASE_NAME)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "Username cannot be blank")
    @Column(nullable = false,unique = true)
    private String username;

    @NotBlank(message = "Name cannot be null")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Email cannot be blank")
    @Column(nullable = false)
    @Email
    private String email;

    private String avatarUrl;
}
