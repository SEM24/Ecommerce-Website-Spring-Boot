package com.khomsi.site_project.service;

import com.khomsi.site_project.entity.Category;
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
    public List<Order> getAllOrdersByUser(User user) {
        return orderRepository.findOrdersByUser(user);
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

    @Override
    public Order getOrderByUser(User user) throws OrderNotFoundException {
        Order order = orderRepository.findByUser(user);
        if (order == null) {
            throw new OrderNotFoundException("Couldn't find any order with ID " + order.getId());
        }
        return order;
    }

    @Override
    public float countSum(List<OrderBasket> orderBaskets) {
        float sum = 0;
        for (OrderBasket orderBasket : orderBaskets) {
            sum += orderBasket.getSubtotal();
        }
        return sum;
    }

    @Override
    public void deleteOrder(int id) {
        orderRepository.deleteById(id);
    }
}
