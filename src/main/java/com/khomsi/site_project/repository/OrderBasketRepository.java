package com.khomsi.site_project.repository;

import com.khomsi.site_project.entity.OrderBasket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderBasketRepository extends JpaRepository<OrderBasket, Integer> {
}