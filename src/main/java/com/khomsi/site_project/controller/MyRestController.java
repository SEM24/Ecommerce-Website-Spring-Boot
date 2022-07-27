package com.khomsi.site_project.controller;

import com.khomsi.site_project.entity.Product;
import com.khomsi.site_project.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MyRestController {
    @Autowired
    private IProductService productService;

    @GetMapping("/products")
    private List<Product> showAllCustomers() {
        return productService.getAllProducts();
    }

    @GetMapping("/products/{id}")
    private Product showProduct(@PathVariable int id) {
        return productService.getProduct(id);
    }

    @PostMapping("/products")
    private Product addNewProduct(@RequestBody Product product) {
        productService.saveProduct(product);
        return product;
    }

    @PutMapping("/products")
    private Product updateProduct(@RequestBody Product product) {
        productService.saveProduct(product);
        return product;
    }

    @DeleteMapping("/products/{id}")
    private String deleteProduct(@PathVariable int id) {
        // Product product = IProductService.getProduct(id);
        productService.deleteProduct(id);
        return "Product with id = " + id + " was deleted";
    }
}
