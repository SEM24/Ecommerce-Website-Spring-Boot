package com.khomsi.site_project.service;

import com.khomsi.site_project.entity.User;

//Тут еще хз, обдумать над
import java.util.List;

public interface UserService {
    void deleteUser(Integer id);

    User findById(int id);

    List<User> findAll();
}
