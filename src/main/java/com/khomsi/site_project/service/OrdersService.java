package com.khomsi.site_project.service;

import com.khomsi.site_project.entity.Orders;
import com.khomsi.site_project.repository.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class OrdersService implements IOrdersService {
    @Autowired
    private OrdersRepository ordersRepository;

    @Override
    public List<Orders> getAllOrders() {
        return ordersRepository.findAll();
    }

    @Override
    public void saveOrder(Orders orders) {
        ordersRepository.save(orders);
    }

    @Override
    public Orders getOrder(int id) {
        Orders orders = null;
        Optional<Orders> optional = ordersRepository.findById(id);
        if (optional.isPresent()) orders = optional.get();
        return orders;
    }

    @Override
    public void deleteOrder(int id) {
        ordersRepository.deleteById(id);
    }
}
