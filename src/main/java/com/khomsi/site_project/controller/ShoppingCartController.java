package com.khomsi.site_project.controller;

import com.khomsi.site_project.entity.OrderBasket;
import com.khomsi.site_project.service.OrderBasketService;
import com.khomsi.site_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class ShoppingCartController {
    @Autowired
    private OrderBasketService orderBasketService;

    @Autowired
    private UserService userService;

    /**
     * User must login before use orderBasket
     *
     * @param model
     * @return
     */
    @GetMapping("/basket")
    public String showShoppingCard(Model model,
                                   Principal principal) {
        //TODO тут исправить
        if (principal != null) {
            List<OrderBasket> orderBaskets = userService.getUserByLogin(principal.getName()).getOrderBaskets();

            model.addAttribute("orderBaskets", orderBaskets);
            //TODO проверить нужно ли это
            model.addAttribute("pageTitle", "Shopping Cart");
        } else return "/login";
//        return "shopping_card";
        return "shopping-cart";
    }

    //TODO реализовать этот метод
    @GetMapping("/removeProduct/{id}")
    public String removeProductFromBasket() {
        return null;
    }

//    @GetMapping("/basket")
//    public String showShoppingCard(Model model,
//                                   Principal principal) {
//        //TODO тут возможно надо вернуть в модель просто лист из корзины
//        if (principal != null) {
//            List<OrderBasket> orderBaskets = userService.getUserByLogin(principal.getName()).getOrderBaskets();
//            OrderBasket orderBasket = null;
//            if (!orderBaskets.isEmpty()) {
//                orderBasket = orderBaskets.get(orderBaskets.size() - 1);
//            }
//            model.addAttribute("orderBasket", orderBasket);
//            model.addAttribute("pageTitle", "Shopping Cart");
//        }
//        return "shopping_card";
//    }
}
