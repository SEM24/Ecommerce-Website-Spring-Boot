package com.khomsi.site_project.controller;

import com.khomsi.site_project.entity.User;
import com.khomsi.site_project.service.ProductService;
import com.khomsi.site_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class AdminTools {
    @Autowired
    private UserService userService;

    @PostMapping("/users/check_login")
    public @ResponseBody String checkLoginUnique(@Param("id") Integer id, @Param("login") String login) {
        return userService.isLoginUnique(id, login);
    }

    /*
    Added this method to check unique login for user while he's registration
    You can modify it to have more unique fields.
    (if you need unique fields in admin panel for user, change another methods like isLoginUnique)
    */
    @PostMapping("/user/check")
    public @ResponseBody String checkLoginRegistration(@Param("login") String login) {
        return userService.checkLoginRegistration(login) ? "OK" : "Duplicate";
    }

    //Controller in admin panel for users
    @GetMapping("/admin/users/page/{pageNum}")
    public String listByPage(@PathVariable(name = "pageNum") int pageNum, Model model) {
        Page<User> page = userService.listByPage(pageNum);
        List<User> listUsers = page.getContent();

        long startCount = (pageNum - 1) * UserService.USER_PER_PAGE + 1;
        long endCount = startCount + UserService.USER_PER_PAGE - 1;

        pageCountMethod(pageNum, model, page, startCount, endCount);

        model.addAttribute("users", listUsers);

        return "admin/user/users";
    }

    public void pageCountMethod(@PathVariable("pageNum") int pageNum, Model model, Page<?> page,
                                 long startCount, long endCount) {
        if (endCount > page.getTotalElements()) {
            endCount = page.getTotalElements();
        }
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("startCount", startCount);
        model.addAttribute("endCount", endCount);
        model.addAttribute("totalItems", page.getTotalElements());
    }
}
