package com.khomsi.site_project.controller;

import com.khomsi.site_project.entity.OrderBasket;
import com.khomsi.site_project.repository.OrderBasketRepository;
import com.khomsi.site_project.repository.ProductRepository;
import com.khomsi.site_project.service.OrderBasketService;
import com.khomsi.site_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class ShoppingCartController {
    @Autowired
    private OrderBasketService orderBasketService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderBasketRepository orderBasketRep;

    @Autowired
    private ProductRepository productRep;
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
        return "shopping-cart";
    }

}
