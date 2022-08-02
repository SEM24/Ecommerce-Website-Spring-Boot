package com.khomsi.site_project.repository;

import com.khomsi.site_project.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}
