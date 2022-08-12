package com.khomsi.site_project.service;

import com.khomsi.site_project.entity.Order;
import com.khomsi.site_project.entity.OrderBasket;
import com.khomsi.site_project.entity.User;
import com.khomsi.site_project.exception.OrderNotFoundException;

import java.util.List;

public interface IOrdersService {
    public List<Order> getAllOrders();

    public void saveOrder(Order orders);

    public Order getOrder(int id);

//    Order getOrderByUserId(Integer user) throws OrderNotFoundException;

    public void deleteOrder(int id);


}
