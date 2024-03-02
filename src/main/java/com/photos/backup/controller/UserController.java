package com.photos.backup.controller;


import com.photos.backup.dto.ResponseDTO;
import com.photos.backup.dto.UserDTO;
import com.photos.backup.entity.User;
import com.photos.backup.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    UserService userService;


    @PostMapping("/register")
    private ResponseEntity<ResponseDTO<UserDTO>> register(@RequestBody User user){
        ResponseDTO<UserDTO> responseDTO = ResponseDTO.noErrorResponse(UserDTO.fromUser(userService.save(user)));
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
    @GetMapping
    private ResponseEntity<ResponseDTO<UserDTO>> getUser(){
       String id = SecurityContextHolder.getContext().getAuthentication().getName();
        ResponseDTO<UserDTO> responseDTO = ResponseDTO.noErrorResponse(UserDTO.fromUser(userService.get(id)));
        return new ResponseEntity<>(responseDTO,HttpStatus.OK);
    }

    @DeleteMapping
    private ResponseEntity<HttpStatus> deleteUser(){
        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping
    private ResponseEntity<ResponseDTO<UserDTO>> updateUser(@RequestBody User user){
        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        ResponseDTO<UserDTO> responseDTO = ResponseDTO.noErrorResponse(UserDTO.fromUser(userService.update(id,user)));
        return new ResponseEntity<>(responseDTO,HttpStatus.OK);
    }
}
