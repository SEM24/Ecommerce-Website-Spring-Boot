package com.khomsi.site_project.controller;

import com.khomsi.site_project.entity.*;
import com.khomsi.site_project.exception.CategoryNotFoundException;
import com.khomsi.site_project.exception.ProductNotFoundException;
import com.khomsi.site_project.exception.UserNotFoundException;
import com.khomsi.site_project.repository.VendorRepository;
import com.khomsi.site_project.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private VendorRepository vendorRep;

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
    public String allProducts(Model model) {
        try {
            List<Product> products = productService.getAllProducts();
            model.addAttribute("allProducts", products);
        } catch (ProductNotFoundException exception) {
            model.addAttribute("error", exception.getLocalizedMessage());
            return "error/404";
        }
        return "admin/product/all-product";
    }

    @GetMapping("/products/edit/{id}")
    public String updateProduct(@PathVariable int id, Model model, RedirectAttributes attributes) {
        try {
            Product product = productService.getProduct(id);
            List<Vendor> vendorList = vendorRep.findAll();
            List<Category> categoryList = categoryService.listCategoriesUserInForm();
            model.addAttribute("updateProduct", product);
            model.addAttribute("vendorList", vendorList);
            model.addAttribute("categoryList", categoryList);
            return "admin/product/update-product";
        } catch (ProductNotFoundException e) {
            attributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/admin/products";
        }
    }

    @GetMapping("/products/add")
    public String addProduct(Model model) {

        List<Vendor> vendorList = vendorRep.findAll();
        List<Category> categoryList = categoryService.listCategoriesUserInForm();
        model.addAttribute("product", new Product());
        model.addAttribute("vendorList", vendorList);
        model.addAttribute("categoryList", categoryList);

        return "admin/product/add-product";
    }

    @PostMapping("/products/save")
    public String saveProduct(Product product) {
        productService.saveProduct(product);
        return "redirect:/admin/products";
    }

    @PostMapping("/products/{id}/delete")
    public String deleteProduct(@PathVariable int id, Model model) {
        try {
            productService.deleteProduct(id);
        } catch (ProductNotFoundException e) {
            model.addAttribute("error", e.getCause().getMessage());
            return "/error/404";
        }
        return "redirect:/admin/products";
    }

    @GetMapping("/users")
    public String listFirstPage(Model model) {

        return adminTools.listByPage(1, model);
    }
    @GetMapping("/users/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("userInfo", new UserInfo());
        model.addAttribute("roles", Role.values());
        model.addAttribute("pageTitle", "Create New User");
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
            model.addAttribute("pageTitle", "Edit User (ID: " + id + ")");
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
    public String allCategories(Model model) {
        List<Category> categories = categoryService.listAll();
        model.addAttribute("allCategories", categories);

        return "admin/category/all-categories";
    }

    @GetMapping("/categories/edit/{id}")
    public String updateCategory(@PathVariable int id, Model model, RedirectAttributes attributes) {
        try {
            Category category = categoryService.getCategory(id);
            List<Category> categoryList = categoryService.listCategoriesUserInForm();
            model.addAttribute("updateCategory", category);
            model.addAttribute("categoryList", categoryList);
            return "admin/category/update-category";
        } catch (CategoryNotFoundException e) {
            attributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/admin/categories";
        }
    }

    @GetMapping("/categories/add")
    public String addCategory(Model model) {
        List<Category> categoryList = categoryService.listCategoriesUserInForm();
        model.addAttribute("addCategory", new Category());
        model.addAttribute("categoryList", categoryList);

        return "admin/category/add-category";
    }

    @PostMapping("/categories/save")
    public String saveCategory(@ModelAttribute Category category, RedirectAttributes attributes) {
        categoryService.saveCategory(category);
        attributes.addFlashAttribute("message", "The category has been saved successfully");
        return "redirect:/admin/categories";
    }

    @PostMapping("/categories/{id}/delete")
    public String deleteCategory(@PathVariable int id) {
        categoryService.deleteCategory(id);
        return "redirect:/admin/categories";
    }


    @GetMapping("/vendors")
    public String allVendors(Model model) {
        List<Vendor> vendors = vendorRep.findAll();
        model.addAttribute("allVendors", vendors);

        return "admin/vendor/all-vendors";
    }

    @GetMapping("/vendors/edit/{id}")
    public String updateVendor(@PathVariable int id, Model model) {
        Vendor vendor = vendorRep.getReferenceById(id);
        model.addAttribute("updateVendor", vendor);
        return "admin/vendor/update-vendor";
    }

    @PostMapping("/vendors/{id}/delete")
    public String deleteVendor(@PathVariable int id) {
        vendorRep.deleteById(id);
        return "redirect:/admin/vendors";
    }

    @GetMapping("/vendors/add")
    public String addVendor(Model model) {
        Vendor vendor = new Vendor();
        model.addAttribute("addVendor", vendor);
        return "admin/vendor/add-vendor";
    }

    @PostMapping("/vendors/save")
    public String createVendor(Vendor vendor) {
        vendorRep.save(vendor);
        return "redirect:/admin/vendors";
    }

    @GetMapping("/orders")
    public String allOrders(Model model) {
        List<Order> orders = ordersService.getAllOrders();
        model.addAttribute("allOrders", orders);

        return "admin/orders/all-orders";
    }

    @GetMapping("/orders/edit/{id}")
    public String updateOrder(@PathVariable int id, Model model) {
        Order order = ordersService.getOrder(id);
        model.addAttribute("updateOrder", order);
        return "admin/orders/update-order";
    }

    @PostMapping("/orders/save")
    public String saveOrder(Order orders) {
        ordersService.saveOrder(orders);
        return "redirect:/admin/orders";
    }

    @PostMapping("/orders/{id}/delete")
    public String deleteOrder(@PathVariable int id) {
        ordersService.deleteOrder(id);
        return "redirect:/admin/orders";
    }

    @GetMapping("/order_baskets")
    public String allOrderBasket(Model model) {
        try {
            model.addAttribute("allOrderBaskets", orderBasketService.getAllOrderBaskets());
        } catch (NotFoundException ex) {
            model.addAttribute("error", ex.getCause().getCause().getMessage());
            return "/error/404";
        }
        return "admin/order_basket/all-order_baskets";
    }
}
