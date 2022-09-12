package com.khomsi.site_project.service;

import com.khomsi.site_project.entity.User;
import com.khomsi.site_project.exception.UserNotFoundException;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IUserService {

    public List<User> getAllUsers();

    public void saveUser(User user);

    void encodePassword(User user);

    String isLoginUnique(Integer id, String login);

    boolean checkLoginRegistration(String login);

    public User getUser(int id) throws UserNotFoundException;

    public User getUserByLogin(String login);

    public void deleteUser(Integer id) throws UserNotFoundException;

    Page<User> listByPage(int pageNum);
}
