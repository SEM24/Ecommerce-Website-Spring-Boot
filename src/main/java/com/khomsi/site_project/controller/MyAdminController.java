package com.khomsi.site_project.controller;

import com.khomsi.site_project.entity.*;
import com.khomsi.site_project.exception.CategoryNotFoundException;
import com.khomsi.site_project.exception.ProductNotFoundException;
import com.khomsi.site_project.repository.*;
import com.khomsi.site_project.service.CategoryService;
import com.khomsi.site_project.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class MyAdminController {
//    @Autowired
//    private ProductRepository productRep;

    @Autowired
    private ProductService productService;
    @Autowired
    private UserRepository userRep;
    @Autowired
    private UserInfoRepository userDetailsRep;

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private VendorRepository vendorRep;

    @Autowired
    private OrderRepository ordersRep;

    @Autowired
    private OrderBasketRepository orderBasketRep;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping({"", "/", "/admin-panel"})
    public String showAdminPanel() {
        return "admin/admin-panel";
    }

    @GetMapping("/products")
    public String allProducts(Model model) {
        List<Product> products = productService.getAllProducts();
        model.addAttribute("allProducts", products);

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
            attributes.addFlashAttribute("message", e.getMessage());
            return "redirect:/admin/products";
        }
    }

    @PostMapping("/products/save")
    public String saveProduct(Product product, RedirectAttributes attributes) {
        productService.saveProduct(product);
        return "redirect:/admin/products";
    }

    @PostMapping("/allProducts/{id}/delete")
    public String deleteProduct(@PathVariable int id, Model model) {
        //FIXME doen't show error on the page, but shows in console
        try {
            productService.deleteProduct(id);
        } catch (JpaSystemException exception) {
            model.addAttribute("error", exception.getCause().getCause().getMessage());
            return "redirect:/admin/products";
        }
        return "redirect:/admin/products";
    }

    @GetMapping("/products/add")
    public String addProduct(Model model) {

        List<Vendor> vendorList = vendorRep.findAll();
        List<Category> categoryList = categoryService.listCategoriesUserInForm();
        model.addAttribute("addProduct", new Product());
        model.addAttribute("vendorList", vendorList);
        model.addAttribute("categoryList", categoryList);

        return "admin/product/add-product";
    }

    @GetMapping("/users")
    public String allUsers(Model model) {
        List<User> users = userRep.findAll();
        model.addAttribute("allUsers", users);

        return "admin/user/all-users";
    }

    @GetMapping("/users/edit/{id}")
    public String updateUser(@PathVariable int id, Model model) {
        User user = userRep.getReferenceById(id);
        model.addAttribute("updateUser", user);
        return "admin/user/update-user";
    }

    @PostMapping("/users/save")
    public String saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRep.save(user);
        return "redirect:/admin/users";
    }

    @PostMapping("/allUsers/{id}/delete")
    public String deleteUser(@PathVariable int id) {
        userRep.deleteById(id);
        return "redirect:/admin/users";
    }

    @GetMapping("/users/add")
    public String addUser(Model userModel, Model userDetailsModel) {
        userModel.addAttribute("addUser", new User());
        userDetailsModel.addAttribute("addUserDetails", new UserInfo());
        return "admin/user/add-user";
    }

    @PostMapping("/users/add")
    public String createUser(UserInfo userInfo, User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRep.save(user);
        userInfo.setUser(user);
        userDetailsRep.save(userInfo);
        return "redirect:/admin/users";
    }

    @GetMapping("/user_details")
    public String allUserDetails(Model model) {
        List<UserInfo> userDetails = userDetailsRep.findAll();
        model.addAttribute("allUserDetails", userDetails);

        return "admin/userDetails/all-userDetails";
    }

    @GetMapping("/user_details/edit/{id}")
    public String updateUserDetails(@PathVariable int id, Model model) {
        UserInfo userInfo = userDetailsRep.getReferenceById(id);
        model.addAttribute("updateUserDetails", userInfo);
        return "admin/userDetails/update-userDetails";
    }

    @PostMapping("/user_details/save")
    public String saveUserDetails(UserInfo userInfo) {
        userDetailsRep.save(userInfo);
        return "redirect:/admin/user_details";
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

    @GetMapping("/categories/add")
    public String addCategory(Model model) {
        List<Category> categoryList = categoryService.listCategoriesUserInForm();
        model.addAttribute("addCategory", new Category());
        model.addAttribute("categoryList", categoryList);

        return "admin/category/add-category";
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
        List<Order> orders = ordersRep.findAll();
        model.addAttribute("allOrders", orders);

        return "admin/orders/all-orders";
    }

    @GetMapping("/orders/edit/{id}")
    public String updateOrder(@PathVariable int id, Model model) {
        Order order = ordersRep.getReferenceById(id);
        model.addAttribute("updateOrder", order);
        return "admin/orders/update-order";
    }

    @PostMapping("/orders/save")
    public String saveOrder(Order orders) {
        ordersRep.save(orders);
        return "redirect:/admin/orders";
    }

    @PostMapping("/orders/{id}/delete")
    public String deleteOrder(@PathVariable int id) {
        ordersRep.deleteById(id);
        return "redirect:/admin/orders";
    }

    @GetMapping("/order_baskets")
    public String allOrderBasket(Model model) {
        List<OrderBasket> orderBaskets = orderBasketRep.findAll();
        model.addAttribute("allOrderBaskets", orderBaskets);

        return "admin/order_basket/all-order_baskets";
    }
}
