package com.khomsi.site_project.controller;

import com.khomsi.site_project.entity.Product;
import com.khomsi.site_project.repository.ProductRepository;
import com.khomsi.site_project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class MyAdminController {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping({"", "/"})
    public String showAdminPanel() {
        return "/admin/admin-panel";
    }

    @GetMapping("/products")
    public String productPage() {
        return "/admin/product-page";
    }

    @GetMapping("/allProducts")
    public String allProduct(Model model) {
        List<Product> products = productRepository.findAll();
        model.addAttribute("product", products);

        return "/admin/all-product";
    }
}
