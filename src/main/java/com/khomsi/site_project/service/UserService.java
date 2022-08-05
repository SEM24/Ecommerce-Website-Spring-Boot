package com.khomsi.site_project.service;

import com.khomsi.site_project.entity.User;
import com.khomsi.site_project.entity.UserInfo;
import com.khomsi.site_project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public User getUser(int id) {
        User user = null;
        Optional<User> optional = userRepository.findById(id);
        if (optional.isPresent()) {
            user = optional.get();
        }
        return user;
    }

    @Override
    public User getUserByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Override
    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }

//    public User getCurrentlyLoggedInUser(Authentication authentication) {
//        if (authentication == null) return null;
//
//        User user = null;
//        Object principal = authentication.getPrincipal();
//
//        if (principal instanceof UserInfo) {
//            user = ((UserInfo) principal).getUser();
//        } else if (principal instanceof )
//    }
}
