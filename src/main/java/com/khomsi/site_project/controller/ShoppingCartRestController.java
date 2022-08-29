package com.khomsi.site_project.controller;

import com.khomsi.site_project.entity.User;
import com.khomsi.site_project.service.OrderBasketService;
import com.khomsi.site_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class ShoppingCartRestController {
    @Autowired
    private OrderBasketService orderBasketService;
    @Autowired
    private UserService userService;

    @PostMapping("/basket/add/{pid}/{qty}")
    public String addProductToBasket(@PathVariable("pid") Integer productId,
                                     @PathVariable("qty") Integer quantity,
                                     Principal principal) {
        if (principal == null) {
            return "You must login to add this product to your shopping basket";
        }
        User user = userService.getUserByLogin(principal.getName());

        if (user == null) return "You must login to add this product to your shopping basket";

        Integer addedQuantity = orderBasketService.addProduct(productId, quantity, user);

        return addedQuantity > 1 ? addedQuantity + " items of this product were added to your shopping basket"
                : addedQuantity + " item of this product was added to your shopping basket";
    }

    @PostMapping("/basket/update/{pid}/{qty}")
    public String updateQuantity(@PathVariable("pid") Integer productId,
                                 @PathVariable("qty") Integer quantity,
                                 Principal principal) {
        if (principal == null) {
            return "You must login to update quantity";
        }
        User user = userService.getUserByLogin(principal.getName());

        if (user == null) return "You must login to update quantity";

        float subtotal = orderBasketService.updateQuantity(productId, quantity, user);

        return String.valueOf(subtotal);
    }

    @PostMapping("/basket/remove/{pid}")
    public String removeProductFromBasket(@PathVariable("pid") Integer productId,
                                          Principal principal) {
        if (principal == null) {
            return "You must login to remove product";
        }
        User user = userService.getUserByLogin(principal.getName());

        if (user == null) return "You must login to remove product";

        orderBasketService.removeProduct(productId, user);

        return "The product has been removed from your shopping basket.";
    }


}
