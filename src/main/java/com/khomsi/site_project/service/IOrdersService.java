package com.khomsi.site_project.service;

import com.khomsi.site_project.entity.Order;

import java.util.List;

public interface IOrdersService {
    public List<Order> getAllOrders();

    public void saveOrder(Order orders);

    public Order getOrder(int id);

    public void deleteOrder(int id);
}
