package com.khomsi.site_project.service;

import com.khomsi.site_project.entity.User;

import java.util.List;

public interface IUserService {

    public List<User> getAllUsers();

    public void saveUser(User user);

    public User getUser(int id);

    public User getUserByLogin(String login);

    public void deleteUser(int id);

}
