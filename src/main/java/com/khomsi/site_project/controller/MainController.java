package com.khomsi.site_project.controller;

import com.khomsi.site_project.entity.*;
import com.khomsi.site_project.exception.OrderNotFoundException;
import com.khomsi.site_project.repository.CategoryRepository;
import com.khomsi.site_project.repository.OrderStatusRepository;
import com.khomsi.site_project.repository.UserRepository;
import com.khomsi.site_project.service.OrdersService;
import com.khomsi.site_project.service.ProductService;
import com.khomsi.site_project.service.UserService;
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

import java.security.Principal;
import java.util.List;

@Controller
public class MainController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryRepository categoryRep;

    @Autowired
    private OrdersService ordersService;
    @Autowired
    private OrderStatusRepository orderStatusRep;

    @GetMapping("/")
    public String index() {
        return "index";
    }


    @GetMapping("/login")
    public String login() {
        //TODO тут исправить

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
        UserInfo userInfo = new UserInfo();
        userModel.addAttribute("user", user);
        userDetailsModel.addAttribute("userInfo", userInfo);

        return "registration";
    }

    @PostMapping("/registration")
    public String registration(User user, UserInfo userInfo, Model model) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER);
        user.setUserInfo(userInfo);
        userInfo.setUser(user);
        try {
            userRepository.save(user);
        } catch (JpaSystemException ex) {
            model.addAttribute("error", ex.getCause().getCause().getMessage());
            model.addAttribute("userInfo", userInfo);
            return "registration";
        }
        return "redirect:/";
    }

    @GetMapping("/basket")
    public String showShoppingCard(Model model,
                                   Principal principal) {
        //TODO тут исправить
        if (principal != null) {
            List<OrderBasket> orderBaskets = userService.getUserByLogin(principal.getName()).getOrderBaskets();

            model.addAttribute("orderBaskets", orderBaskets);
            model.addAttribute("order", new Order());
        } else return "/login";
        return "shopping-cart";
    }

    @GetMapping("/category")
    public String showCategories(Model model) {
        List<Category> listEnabledCategories = categoryRep.findAllEnabled();
        model.addAttribute("listCategories", listEnabledCategories);
        return "category";
    }

    //TODO make test for methods under and html pages
    @GetMapping("/orders")
    public String showOrders(Model model) {
        List<Order> orders = ordersService.getAllOrders();
        model.addAttribute("orders", orders);
        return "orders";
    }

//    @GetMapping("/payment")
//    public String makeOrder(Model model, Principal principal) {
//        User user = userRepository.findByLogin(principal.getName());
//
//        try {
//            Order order = ordersService.getOrderByUserId(user.getId());
//            model.addAttribute("order", order);
//        } catch (OrderNotFoundException e) {
//            model.addAttribute("error", e.getMessage());
//            return "/error/404";
//        }
//        return "payment";
//    }

    @PostMapping("/savePreOrder")
    public String savePreOrder(Order order, OrderBasket orderBasket, Model model) {
        order.setOrderStatus(orderStatusRep.getReferenceById(4));
        order.setOrderBasket(orderBasket);
        order.setShippingType(0);
        order.setTotalPrice(orderBasket.getSubtotal());
        try {
            ordersService.saveOrder(order);
        } catch (JpaSystemException ex) {
            model.addAttribute("error", ex.getCause().getCause().getMessage());
            model.addAttribute("orderBasket", orderBasket);
            return "error/404";
        }
        return "redirect:/payment";
    }

}
