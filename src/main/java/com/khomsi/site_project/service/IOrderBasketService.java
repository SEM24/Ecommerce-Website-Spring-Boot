package com.khomsi.site_project.service;

import com.khomsi.site_project.entity.OrderBasket;
import com.khomsi.site_project.entity.User;

import java.util.List;

public interface IOrderBasketService {
    List<OrderBasket> getAllOrderBaskets();

    public List<OrderBasket> listOrderBasket(User user);

    public Integer addProduct(Integer productId, Integer quantity, User user);

    public float updateQuantity(Integer productId, Integer quantity, User user);

    public void removeProduct(Integer productId, User user);
}
