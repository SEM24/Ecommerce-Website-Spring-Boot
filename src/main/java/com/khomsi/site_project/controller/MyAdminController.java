package com.khomsi.site_project.controller;

import com.khomsi.site_project.entity.Product;
import com.khomsi.site_project.entity.User;
import com.khomsi.site_project.entity.UserDetails;
import com.khomsi.site_project.repository.ProductRepository;
import com.khomsi.site_project.repository.UserDetailsRepository;
import com.khomsi.site_project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class MyAdminController {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @GetMapping({"", "/", "/admin-panel"})
    public String showAdminPanel() {
        return "admin/admin-panel";
    }

    @GetMapping("/allProducts")
    public String allProducts(Model model) {
        List<Product> products = productRepository.findAll();
        model.addAttribute("allProducts", products);

        return "admin/product/all-product";
    }

    @GetMapping("/allProducts/{id}")
    public String updateProduct(@PathVariable int id, Model model) {
        Product product = productRepository.getReferenceById(id);
        model.addAttribute("updateProduct", product);
        return "admin/product/update-product";
    }

    @PostMapping("/allProducts/{id}")
    public String saveProduct(@PathVariable int id, @ModelAttribute Product product) {
        Product newProduct = productRepository.getReferenceById(id);

        newProduct.setTitle(product.getTitle());
        newProduct.setDescription(product.getDescription());
        newProduct.setPrice(product.getPrice());
        newProduct.setVendor(product.getVendor());
        newProduct.setCategory(product.getCategory());
        productRepository.save(newProduct);
        return "redirect:/admin/allProducts";
    }

    @PostMapping("/allProducts/{id}/delete")
    public String deleteProduct(@PathVariable int id) {
        productRepository.deleteById(id);
        return "redirect:/admin/allProducts";
    }

    @GetMapping("/addProduct")
    public String addProduct(Model model) {
        Product product = new Product();
        model.addAttribute("addProduct", product);
        return "admin/product/add-product";
    }

    @PostMapping("/addProduct")
    public String createProduct(Product product) {
        productRepository.save(product);
        return "redirect:/admin/allProducts";
    }

    @GetMapping("/allUsers")
    public String allUsers(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("allUsers", users);

        return "admin/user/all-users";
    }

    @GetMapping("/allUsers/{id}")
    public String updateUser(@PathVariable int id, Model model) {
        User user = userRepository.getReferenceById(id);
        model.addAttribute("updateUser", user);
        return "admin/user/update-user";
    }

    @PostMapping("/allUsers/{id}")
    public String saveUser(@PathVariable int id, @ModelAttribute User user) {
        User newUser = userRepository.getReferenceById(id);
        newUser.setLogin(user.getLogin());
        newUser.setPassword(user.getPassword());
        newUser.setRole(user.getRole());
        newUser.setQty(user.getQty());
        userRepository.save(newUser);
        return "redirect:/admin/allUsers";
    }

    @PostMapping("/allUsers/{id}/delete")
    public String deleteUser(@PathVariable int id) {
        userRepository.deleteById(id);
        return "redirect:/admin/allUsers";
    }

    @GetMapping("/addUser")
    public String addUser(Model userModel, Model userDetailsModel) {
        User user = new User();
        UserDetails userDetails = new UserDetails();
        userModel.addAttribute("addUser", user);
        userDetailsModel.addAttribute("addUserDetails", userDetails);
        return "admin/user/add-user";
    }

    @PostMapping("/addUser")
    public String createUser(UserDetails userDetails, User user) {
        userRepository.save(user);
        userDetails.setUser(user);
        userDetailsRepository.save(userDetails);
        return "redirect:/admin/allUsers";
    }

    @GetMapping("/allUserDetails")
    public String allUserDetails(Model model) {
        List<UserDetails> userDetails = userDetailsRepository.findAll();
        model.addAttribute("allUserDetails", userDetails);

        return "admin/userDetails/all-userDetails";
    }

    @GetMapping("/allUserDetails/{id}")
    public String updateUserDetails(@PathVariable int id, Model model) {
        UserDetails userDetails = userDetailsRepository.getReferenceById(id);
        model.addAttribute("updateUserDetails", userDetails);
        return "admin/userDetails/update-userDetails";
    }

    @PostMapping("/allUserDetails/{id}")
    public String saveUserDetails(@PathVariable int id, @ModelAttribute UserDetails userDetails) {
        UserDetails newUserDetails = userDetailsRepository.getReferenceById(id);
        newUserDetails.setName(userDetails.getName());
        newUserDetails.setPhone(userDetails.getPhone());
        newUserDetails.setEmail(userDetails.getEmail());
        newUserDetails.setCity(userDetails.getCity());
        userDetailsRepository.save(newUserDetails);
        return "redirect:/admin/allUserDetails";
    }

    //FIXME тут мем какой-то, детали не удаляет, а просто страницу обновляет
//    @PostMapping("/allUserDetails/{id}/delete")
//    public String deleteUserDetails(@PathVariable int id) {
//        userDetailsRepository.deleteById(id);
//        return "redirect:/admin/allUserDetails";
//    }
}
