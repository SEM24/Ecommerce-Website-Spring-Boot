package com.khomsi.site_project.service;

import com.khomsi.site_project.entity.User;
import com.khomsi.site_project.exception.UserNotFoundException;
import com.khomsi.site_project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public static final int USER_PER_PAGE = 4;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void saveUser(User user) {
        boolean isUpdatedUser = (user.getId() != null);
        if (isUpdatedUser) {
            User existingUser = userRepository.getReferenceById(user.getId());

            if (user.getPassword().isEmpty()) {
                user.setPassword(existingUser.getPassword());
            } else {
                encodePassword(user);
            }
        } else {
            encodePassword(user);
        }
        userRepository.save(user);
    }

    @Override
    public User getUser(int id) throws UserNotFoundException {
        try {
            return userRepository.getReferenceById(id);
        } catch (NoSuchElementException ex) {
            throw new UserNotFoundException("Couldn't find any user with id " + id);
        }
    }

    @Override
    public User getUserByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Override
    public void deleteUser(Integer id) throws UserNotFoundException {
        Long countById = userRepository.countById(id);
        if (countById == null || countById == 0) {
            throw new UserNotFoundException("Couldn't find any user with id " + id);
        }
        userRepository.deleteById(id);
    }

    @Override
    public void encodePassword(User user) {
        String encodePass = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodePass);
    }

    @Override
    public String isLoginUnique(Integer id, String login) {
        User userByLogin = userRepository.findByLogin(login);
        boolean isCreatingNew = (id == null);

        if (isCreatingNew) {
            if (userByLogin != null) return "Duplicate";
        } else {
            if (!Objects.equals(userByLogin.getId(), id)) {
                return "Duplicate";
            }
        }
        return "OK";
    }

    /*
    Added this method to check unique login for user while he's registration
    You can modify it to have more unique fields.
    (if you need unique fields in admin panel for user, change another methods like isLoginUnique)
     */
    @Override
    public boolean checkLoginRegistration(String login) {
        User user = userRepository.findByLogin(login);

        return user == null;
    }

    @Override
    public Page<User> listByPage(int pageNum) {
        Pageable pageable = PageRequest.of(pageNum - 1, USER_PER_PAGE);
        return userRepository.findAll(pageable);
    }

}
