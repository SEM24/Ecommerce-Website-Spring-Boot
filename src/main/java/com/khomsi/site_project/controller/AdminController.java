package com.khomsi.site_project.controller;

import com.khomsi.site_project.entity.*;
import com.khomsi.site_project.exception.CategoryNotFoundException;
import com.khomsi.site_project.exception.OrderNotFoundException;
import com.khomsi.site_project.exception.ProductNotFoundException;
import com.khomsi.site_project.exception.UserNotFoundException;
import com.khomsi.site_project.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.webjars.NotFoundException;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private VendorService vendorService;

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private OrderBasketService orderBasketService;

    @Autowired
    private AdminTools adminTools;

    @GetMapping({"", "/", "/admin-panel"})
    public String showAdminPanel() {
        return "admin/admin-panel";
    }

    @GetMapping("/products")
    public String listProductsFirstPage(Model model) {
        return adminTools.listProductsByPage(1, model, "title", "asc", null, 0);
    }

    @GetMapping("/products/edit/{id}")
    public String updateProduct(@PathVariable(name = "id") int id, Model model, RedirectAttributes attributes) {
        try {
            Product product = productService.getProduct(id);
            List<Vendor> vendorList = vendorService.getAllVendors();
            List<Category> categoryList = categoryService.listCategoriesUserInForm();
            model.addAttribute("product", product);
            model.addAttribute("vendorList", vendorList);
            model.addAttribute("categoryList", categoryList);
        } catch (ProductNotFoundException e) {
            attributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/admin/product/products";
        }
        return "admin/product/product_form";
    }

    @GetMapping("/products/new")
    public String addProduct(Model model) {

        List<Vendor> vendorList = vendorService.getAllVendors();
        List<Category> categoryList = categoryService.listCategoriesUserInForm();
        model.addAttribute("product", new Product());
        model.addAttribute("vendorList", vendorList);
        model.addAttribute("categoryList", categoryList);

        return "admin/product/product_form";
    }

    @PostMapping("/products/save")
    public String saveProduct(Product product, RedirectAttributes redirect) {
        productService.saveProduct(product);
        redirect.addFlashAttribute("message", "The product was saved successfully");
        return "redirect:/admin/products";
    }

    @GetMapping("/products/delete/{id}")
    public String deleteProduct(@PathVariable(name = "id") Integer id, RedirectAttributes redirect) {
        try {
            productService.deleteProduct(id);
            redirect.addFlashAttribute("message",
                    "The product ID " + id + " has been deleted successfully");
        } catch (ProductNotFoundException e) {
            redirect.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/admin/products";
    }

    @GetMapping("/users")
    public String listUsersFirstPage(Model model) {
        return adminTools.listUsersByPage(1, model);
    }

    @GetMapping("/users/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("userInfo", new UserInfo());
        model.addAttribute("roles", Role.values());
        return "admin/user/user_form";
    }

    @PostMapping("/users/save")
    public String createUser(UserInfo userInfo, User user, RedirectAttributes redirect) {
        user.setUserInfo(userInfo);
        userInfo.setUser(user);
        userService.saveUser(user);
        redirect.addFlashAttribute("message", "The user was saved successfully");

        return "redirect:/admin/users";
    }

    @GetMapping("/users/edit/{id}")
    public String updateUser(@PathVariable(name = "id") int id, Model model, RedirectAttributes redirect) {
        try {
            User user = userService.getUser(id);
            UserInfo userInfo = userInfoService.getUserDetail(id);
            model.addAttribute("user", user);
            model.addAttribute("userInfo", userInfo);
            model.addAttribute("roles", Role.values());
        } catch (UserNotFoundException e) {
            redirect.addFlashAttribute("message", e.getMessage());
            return "redirect:/admin/users";
        }
        return "admin/user/user_form";
    }

    @GetMapping("/users/delete/{id}")
    public String deleteUser(@PathVariable(name = "id") Integer id, RedirectAttributes redirect) {
        try {
            userService.deleteUser(id);
            redirect.addFlashAttribute("message",
                    "The user ID " + id + " has been deleted successfully");
        } catch (UserNotFoundException e) {
            redirect.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/admin/users";
    }

    @GetMapping("/categories")
    public String listCategoriesFirstPage(Model model) {
        return adminTools.listCategoriesByPage(1, model);
    }

    @GetMapping("/categories/edit/{id}")
    public String updateCategory(@PathVariable int id, Model model, RedirectAttributes attributes) {
        try {
            Category category = categoryService.getCategory(id);
            List<Category> categoryList = categoryService.listCategoriesUserInForm();
            model.addAttribute("category", category);
            model.addAttribute("categoryList", categoryList);
            return "admin/category/category_form";
        } catch (CategoryNotFoundException e) {
            attributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/admin/categories";
        }
    }

    @GetMapping("/categories/new")
    public String addCategory(Model model) {
        List<Category> categoryList = categoryService.listCategoriesUserInForm();
        model.addAttribute("category", new Category());
        model.addAttribute("categoryList", categoryList);

        return "admin/category/category_form";
    }

    @PostMapping("/categories/save")
    public String saveCategory(@ModelAttribute Category category, RedirectAttributes attributes) {
        categoryService.saveCategory(category);
        attributes.addFlashAttribute("message", "The category has been saved successfully");
        return "redirect:/admin/categories";
    }

    @GetMapping("/categories/delete/{id}")
    public String deleteCategory(@PathVariable(name = "id") Integer id, RedirectAttributes redirect) {
        try {
            categoryService.deleteCategory(id);
            redirect.addFlashAttribute("message",
                    "The category ID " + id + " has been deleted successfully");
        } catch (CategoryNotFoundException e) {
            redirect.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/admin/categories";
    }

    @GetMapping("/vendors")
    public String listVendorsFirstPage(Model model) {
        return adminTools.listVendorsByPage(1, model);
    }

    @GetMapping("/vendors/new")
    public String newVendor(Model model) {
        model.addAttribute("vendor", new Vendor());
        return "admin/vendor/vendor_form";
    }

    @PostMapping("/vendors/save")
    public String createVendor(Vendor vendor, RedirectAttributes redirect) {
        vendorService.saveVendor(vendor);
        redirect.addFlashAttribute("message", "The vendor was saved successfully");
        return "redirect:/admin/vendors";
    }

    @GetMapping("/vendors/edit/{id}")
    public String updateVendor(@PathVariable(name = "id") int id, Model model, RedirectAttributes redirect) {
        try {
            Vendor vendor = vendorService.getVendor(id);
            model.addAttribute("vendor", vendor);
        } catch (NotFoundException e) {
            redirect.addFlashAttribute("message", e.getMessage());
            return "redirect:/admin/vendors";
        }
        return "admin/vendor/vendor_form";
    }

    @GetMapping("/vendors/delete/{id}")
    public String deleteVendor(@PathVariable(name = "id") Integer id, RedirectAttributes redirect) {
        try {
            vendorService.deleteVendor(id);
            redirect.addFlashAttribute("message",
                    "The vendor ID " + id + " has been deleted successfully");
        } catch (NotFoundException e) {
            redirect.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/admin/vendors";
    }

    @GetMapping("/orders")
    public String listOrdersFirstPage(Model model) {
        return adminTools.listOrdersByPage(1, model);
    }

    @PostMapping("/orders/save")
    public String createOrder(Order order, RedirectAttributes redirect) {
        ordersService.saveOrder(order);

        redirect.addFlashAttribute("message", "The order was saved successfully");
        return "redirect:/admin/orders";
    }

    @GetMapping("/orders/edit/{id}")
    public String updateOrder(@PathVariable(name = "id") int id, Model model, RedirectAttributes redirect) {
        try {
            Order order = ordersService.getOrder(id);
            model.addAttribute("orderTypes", OrderType.values());
            model.addAttribute("order", order);
        } catch (NotFoundException e) {
            redirect.addFlashAttribute("message", e.getMessage());
            return "redirect:/admin/orders";
        }
        return "admin/orders/order_form";
    }

    @GetMapping("/orders/delete/{id}")
    public String deleteOrder(@PathVariable(name = "id") Integer id, RedirectAttributes redirect) {
        try {
            ordersService.deleteOrder(id);
            redirect.addFlashAttribute("message",
                    "The orders ID " + id + " has been deleted successfully");
        } catch (OrderNotFoundException e) {
            redirect.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/admin/orders";
    }

    @GetMapping("/order_baskets")
    public String allOrderBasket(Model model) {
        try {
            model.addAttribute("orderBaskets", orderBasketService.getAllOrderBaskets());
        } catch (NotFoundException ex) {
            model.addAttribute("error", ex.getCause().getCause().getMessage());
            return "/error/404";
        }
        return "admin/order_basket/order_baskets";
    }
}
