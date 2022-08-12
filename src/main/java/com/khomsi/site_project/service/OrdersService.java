package com.khomsi.site_project.service;

import com.khomsi.site_project.entity.Order;
import com.khomsi.site_project.entity.OrderBasket;
import com.khomsi.site_project.entity.User;
import com.khomsi.site_project.exception.OrderNotFoundException;
import com.khomsi.site_project.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrdersService implements IOrdersService {
    @Autowired
    private OrderRepository orderRepository;

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public void saveOrder(Order orders) {
        orderRepository.save(orders);
    }

    @Override
    public Order getOrder(int id) {
        Order orders = null;
        Optional<Order> optional = orderRepository.findById(id);
        if (optional.isPresent()) orders = optional.get();
        return orders;
    }

//    @Override
//    public Order getOrderByUserId(Integer userId) throws OrderNotFoundException {
//        Order order = orderRepository.findByUserId(userId);
//        if (order == null) {
//            throw new OrderNotFoundException("Couldn't find any order with ID " + order.getId());
//        }
//        return order;
//    }

    @Override
    public void deleteOrder(int id) {
        orderRepository.deleteById(id);
    }
}
