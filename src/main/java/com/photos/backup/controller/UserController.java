package com.photos.backup.controller;


import com.photos.backup.entity.User;
import com.photos.backup.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    UserService userService;
    @PostMapping
    private ResponseEntity<User> createUser(@RequestBody User user){
        return new ResponseEntity<>(userService.save(user), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    private ResponseEntity<User> getUser(@PathVariable String id){
        return new ResponseEntity<>(userService.get(id),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<HttpStatus> deleteUser(@PathVariable String id){
        userService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    private ResponseEntity<User> updateUser(@PathVariable String id,@RequestBody User user){
        return new ResponseEntity<>(userService.update(id,user),HttpStatus.OK);
    }
}
