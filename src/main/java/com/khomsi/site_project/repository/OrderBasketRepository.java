package com.khomsi.site_project.repository;

import com.khomsi.site_project.entity.OrderBasket;
import com.khomsi.site_project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderBasketRepository extends JpaRepository<OrderBasket, Integer> {
    List<OrderBasket> findByUser(User user);

}