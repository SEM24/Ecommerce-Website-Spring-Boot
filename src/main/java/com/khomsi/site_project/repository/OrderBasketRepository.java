package com.khomsi.site_project.repository;

import com.khomsi.site_project.entity.OrderBasket;
import com.khomsi.site_project.entity.Product;
import com.khomsi.site_project.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderBasketRepository extends JpaRepository<OrderBasket, Integer> {
    public List<OrderBasket> findByUser(User user);

    public OrderBasket findByUserAndProduct(User user, Product product);

    //It's update/delete query so we use @modifying
    @Query("UPDATE OrderBasket orderBasket SET orderBasket.quantity = ?1 WHERE orderBasket.product.id = ?2 " +
            "AND orderBasket.user.id = ?3")
    @Modifying
    public void updateQuantity(Integer quantity, Integer productId, Integer userId);

    @Query("DELETE FROM OrderBasket orderBasket WHERE orderBasket.user.id = ?1 AND orderBasket.product.id = ?2")
    @Modifying
    public void deleteByUserAndProduct(Integer userId, Integer productId);

}