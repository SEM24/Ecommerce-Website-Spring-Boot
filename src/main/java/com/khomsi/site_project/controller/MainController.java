package com.khomsi.site_project.controller;

import com.khomsi.site_project.entity.Role;
import com.khomsi.site_project.entity.User;
import com.khomsi.site_project.entity.UserDetails;
import com.khomsi.site_project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class MainController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/")
    public String index() {
        return "index";
    }


    @GetMapping("/login")
    public String login() {
        //   If user hasn't been login,give him access to go to url /login
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "login";
        }
        return "redirect:/";
    }

    @GetMapping("/registration")
    public String registration(Model userModel, Model userDetailsModel) {
        User user = new User();
        UserDetails userDetails = new UserDetails();
        userModel.addAttribute("user", user);
        userDetailsModel.addAttribute("userDetails", userDetails);

        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(User user, UserDetails userDetails, Model model) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        user.setUserDetails(userDetails);
        userDetails.setUser(user);
        try {
            userRepository.save(user);
        } catch (JpaSystemException ex) {
            model.addAttribute("error", ex.getCause().getCause().getMessage());
            model.addAttribute("userDetails", userDetails);
            return "registration";
        }
        return "redirect:/";
    }
}
