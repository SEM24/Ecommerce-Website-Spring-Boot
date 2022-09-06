package com.khomsi.site_project.controller;

import com.khomsi.site_project.entity.Order;
import com.khomsi.site_project.entity.OrderBasket;
import com.khomsi.site_project.entity.OrderType;
import com.khomsi.site_project.entity.User;
import com.khomsi.site_project.service.OrdersService;
import com.khomsi.site_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class OrderController {

    @Autowired
    private OrdersService ordersService;
    @Autowired
    private UserService userService;

    @GetMapping("/orders")
    public String showOrders(Model model, Principal principal) {
        if (principal != null) {
            User user = userService.getUserByLogin(principal.getName());
            List<Order> orders = ordersService.getAllOrdersByUser(user);
            model.addAttribute("orders", orders);
        } else return "/error/404";
        return "/user/orders";
    }

    @GetMapping("/payment")
    public String createOrders(Model model, Principal principal) {
        User user = userService.getUserByLogin(principal.getName());
        List<OrderBasket> orderBaskets = user.getOrderBaskets();
        try {
            model.addAttribute("order", new Order());
            model.addAttribute("user", user);
            model.addAttribute("orderBaskets", orderBaskets);
            model.addAttribute("waiting", OrderType.Ожидание);
            model.addAttribute("payed", OrderType.Оплачено);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "/error/404";
        }
        return "checkout";
    }

    @PostMapping("/payment")
    public String saveOrder(Order newOrder, Principal principal, Model model) {
        User user = userService.getUserByLogin(principal.getName());
        List<OrderBasket> orderBaskets = user.getOrderBaskets();
        newOrder.setUser(user);
        newOrder.setTotalPrice(ordersService.countSum(orderBaskets));
        try {
            ordersService.saveOrder(newOrder);
        } catch (JpaSystemException ex) {
            model.addAttribute("error", ex.getCause().getCause().getMessage());
            return "error/404";
        }
        return "redirect:/";
    }
}
