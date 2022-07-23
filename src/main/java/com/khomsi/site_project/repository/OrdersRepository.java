package com.khomsi.site_project.repository;

import com.khomsi.site_project.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrdersRepository extends JpaRepository<Orders, Integer> {
}
