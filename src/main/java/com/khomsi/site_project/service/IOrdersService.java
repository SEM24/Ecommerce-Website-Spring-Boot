package com.khomsi.site_project.service;

import com.khomsi.site_project.entity.Orders;

import java.util.List;

public interface IOrdersService {
    public List<Orders> getAllOrders();

    public void saveOrder(Orders orders);

    public Orders getOrder(int id);

    public void deleteOrder(int id);
}
