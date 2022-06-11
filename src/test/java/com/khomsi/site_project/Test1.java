package com.khomsi.site_project;

import com.khomsi.site_project.entity.EOrderStatus;
import com.khomsi.site_project.entity.OrderStatus;
import com.khomsi.site_project.entity.Orders;
import com.khomsi.site_project.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Test1 {
    public static void main(String[] args) {
        try (SessionFactory factory = new Configuration().configure("hibernate.cfg.xml")
                .addAnnotatedClass(Orders.class)
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(OrderStatus.class)
                .buildSessionFactory(); Session session = factory.getCurrentSession()) {

            User user = new User("Egor", "+3809313", "sandy@shop.com", "Kharkiv", 2);
            OrderStatus orderStatus = new OrderStatus();
            orderStatus.setId(2);

            Orders orders = new Orders(500, orderStatus, 1);
            user.addOrderToUser(orders);

            session.beginTransaction();
            session.save(orders);
            session.getTransaction().commit();
            System.out.println("Done!");

        }
    }
}
