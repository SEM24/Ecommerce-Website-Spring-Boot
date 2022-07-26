package com.khomsi.site_project.service;

import com.khomsi.site_project.entity.UserDetails;
import com.khomsi.site_project.repository.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class UserDetailsService implements IUserDetailsService {
    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Override
    public List<UserDetails> getAllUserDetails() {
        return userDetailsRepository.findAll();
    }

    @Override
    public void saveUserDetail(UserDetails userDetails) {
        userDetailsRepository.save(userDetails);
    }

    @Override
    public UserDetails getUserDetail(int id) {
        UserDetails userDetails = null;
        Optional<UserDetails> optional = userDetailsRepository.findById(id);
        if (optional.isPresent()) {
            userDetails = optional.get();
        }
        return userDetails;
    }

    @Override
    public void deleteUserDetail(int id) {
        userDetailsRepository.deleteById(id);
    }
}
