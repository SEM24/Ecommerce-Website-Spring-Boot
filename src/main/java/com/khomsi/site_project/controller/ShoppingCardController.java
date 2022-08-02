package com.khomsi.site_project.controller;

import com.khomsi.site_project.service.OrderBasketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ShoppingCardController {
    @Autowired
    private OrderBasketService orderBasketService;

}
