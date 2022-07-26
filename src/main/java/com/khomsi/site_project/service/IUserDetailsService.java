package com.khomsi.site_project.service;

import com.khomsi.site_project.entity.UserDetails;

import java.util.List;

public interface IUserDetailsService {

    public List<UserDetails> getAllUserDetails();

    public void saveUserDetail(UserDetails userDetails);

    public UserDetails getUserDetail(int id);

    public void deleteUserDetail(int id);
}
