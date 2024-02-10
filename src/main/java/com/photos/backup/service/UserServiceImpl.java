package com.photos.backup.service;

import com.photos.backup.entity.User;
import com.photos.backup.exception.UsersException;
import com.photos.backup.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.photos.backup.utils.ConversionHelperUtil.fromString;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void delete(String userId) {
        userRepository.deleteById(fromString(userId));
    }

    @Override
    public User update(String userId, User user) {
        User prevUser =  unwrapUser(userRepository.findById(fromString(userId)),userId);
        prevUser.setAvatarUrl(user.getAvatarUrl());
        prevUser.setEmail(user.getEmail());
        prevUser.setName(user.getName());
        userRepository.save(prevUser);
        return  prevUser;
    }

    @Override
    public User get(String userId) {
        return unwrapUser(userRepository.findById(fromString(userId)),userId);
    }


    public static User unwrapUser(Optional<User> user,String userId){
        if(user.isPresent()) return user.get();
        else throw new UsersException(UsersException.UserExceptions.USER_NOT_FOUND,userId);
    }
}

