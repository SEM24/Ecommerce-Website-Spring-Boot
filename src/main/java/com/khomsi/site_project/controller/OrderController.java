package com.khomsi.site_project.controller;

import com.khomsi.site_project.entity.Order;
import com.khomsi.site_project.entity.OrderBasket;
import com.khomsi.site_project.entity.OrderType;
import com.khomsi.site_project.entity.User;
import com.khomsi.site_project.service.OrdersService;
import com.khomsi.site_project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.webjars.NotFoundException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.List;

@Controller
public class OrderController {

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    private OrdersService ordersService;
    @Autowired
    private UserService userService;

    @GetMapping("/orders")
    public String showOrders(Model model, Principal principal) {
        if (principal != null) {
            User user = userService.getUserByLogin(principal.getName());
            List<Order> orders = ordersService.getAllOrdersByUser(user);
            model.addAttribute("orders", orders);
        } else {
            model.addAttribute("error", new NotFoundException("Orders was not found"));
            return "/error/404";
        }
        return "/user/orders";
    }

    @GetMapping("/payment")
    public String createOrders(Model model, Principal principal) {
        if (principal != null) {
            User user = userService.getUserByLogin(principal.getName());
            List<OrderBasket> orderBaskets = user.getOrderBaskets();
            model.addAttribute("order", new Order());
            model.addAttribute("user", user);
            model.addAttribute("orderBaskets", orderBaskets);
            model.addAttribute("waiting", OrderType.Ожидание);
            model.addAttribute("payed", OrderType.Оплачено);
        } else {
            model.addAttribute("error", new NotFoundException("Orders for payment was not found"));
            return "/error/404";
        }
        return "checkout";
    }

    @PostMapping("/payment")
    public String saveOrder(Order newOrder, Principal principal,
                            Model model, RedirectAttributes attributes) {
        User user = userService.getUserByLogin(principal.getName());
        List<OrderBasket> orderBaskets = user.getOrderBaskets();
        newOrder.setUser(user);
        newOrder.setTotalPrice(ordersService.countSum(orderBaskets));
        try {
            ordersService.saveOrder(newOrder);
            attributes.addFlashAttribute("message", "Order was completed! Check your email!");
            sendVerificationEmail(newOrder);
        } catch (JpaSystemException | MessagingException | UnsupportedEncodingException ex) {
            model.addAttribute("error", ex.getCause().getCause().getMessage());
            return "error/404";
        }
        return "redirect:/orders";
    }

    /*
     * Method that creates the email with orders that will be sent to user's email
     * */
    private void sendVerificationEmail(Order order)
            throws MessagingException, UnsupportedEncodingException {

        String shipping = order.getShippingType() == 0 ? "Ukr poshta" : "Nova poshta";

        String subject = "Thank you for ordering in SENKO";
        String senderName = "Senko Store";
        String mailContent = "<p><b>Order number:</b> " + order.getId() + "</p>";
        mailContent += "<p><b>Payment:</b> " + order.getOrderStatus() + "</p>";
        mailContent += "<p><b>Shipping:</b> " + shipping + "</p>";
        mailContent += "<p><b>City:</b> " + order.getCity() + "</p>";
        mailContent += "<p><b>Address:</b> " + order.getAddress() + "</p>";
        mailContent += "<p><b>Order total:</b> " + order.getTotalPrice() + "</p>";

        mailContent += "<hr><img src='cid:logoImage' width=150 />";

        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom("senkoShop@outlook.com", senderName);
        helper.setTo(order.getUser().getEmail());
        helper.setSubject(subject);
        helper.setText(mailContent, true);

        ClassPathResource pathResource = new ClassPathResource("/static/assets/email.gif");
        helper.addInline("logoImage", pathResource);
        javaMailSender.send(message);
    }

}
