package com.photos.backup.service;

import com.photos.backup.entity.User;

public interface UserService {

    User save(User user);

    User get(String userId);

    void delete(String userId);

    User update(String userId,User user);
}
