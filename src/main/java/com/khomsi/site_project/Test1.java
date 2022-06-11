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
                .addAnnotatedClass(com.khomsi.site_project.entity.User.class)
                .buildSessionFactory()) {
            Session session = factory.getCurrentSession();
            User user = new User("Egor", "+3809313", "sandy@shop.com", "Kharkiv", 2);
            OrderStatus orderStatus = new OrderStatus(EOrderStatus.Обработка);
            Orders category =
                    new Orders(500, user, orderStatus, 1);
            session.beginTransaction();
            session.save(category);
            session.getTransaction().commit();
            System.out.println("Done!");

        }
    }
}
