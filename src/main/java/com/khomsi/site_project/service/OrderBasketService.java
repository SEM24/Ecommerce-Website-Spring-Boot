package com.khomsi.site_project.service;

import com.khomsi.site_project.entity.OrderBasket;
import com.khomsi.site_project.entity.User;
import com.khomsi.site_project.repository.OrderBasketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderBasketService implements IOrderBasketService {
    @Autowired
    private OrderBasketRepository orderBasketRep;


    @Override
    public List<OrderBasket> listOrderBasket(User user) {
        return orderBasketRep.findByUser(user);
    }


}
