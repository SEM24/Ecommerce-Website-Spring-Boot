package com.khomsi.site_project.service;

import com.khomsi.site_project.entity.OrderBasket;
import com.khomsi.site_project.entity.Product;
import com.khomsi.site_project.entity.User;
import com.khomsi.site_project.repository.OrderBasketRepository;
import com.khomsi.site_project.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class OrderBasketService implements IOrderBasketService {
    @Autowired
    private OrderBasketRepository orderBasketRep;

    @Autowired
    private ProductRepository productRep;

    @Override
    public List<OrderBasket> getAllOrderBaskets() {
        List<OrderBasket> orderBasket = orderBasketRep.findAll();
        if (orderBasket.isEmpty()) {
            throw new NotFoundException("Couldn't find any product in DB");
        }
        return orderBasket;
    }

    @Override
    public List<OrderBasket> listOrderBasket(User user) {
        return orderBasketRep.findByUser(user);
    }


    @Override
    public Integer addProduct(Integer productId, Integer quantity, User user) {
        Integer addedQuantity = quantity;

        Product product = productRep.getReferenceById(productId);

        OrderBasket orderBasket = orderBasketRep.findByUserAndProduct(user, product);

        if (orderBasket != null) {
            addedQuantity = orderBasket.getQuantity() + quantity;
            orderBasket.setQuantity(addedQuantity);
        }
        //The product hasn't been added to the shopping card by this user
        else {
            orderBasket = new OrderBasket();
            orderBasket.setQuantity(quantity);
            orderBasket.setUser(user);
            orderBasket.setProduct(product);
        }
        orderBasketRep.save(orderBasket);
        return addedQuantity;
    }

    @Override
    public float updateQuantity(Integer productId, Integer quantity, User user) {
        orderBasketRep.updateQuantity(quantity, productId, user.getId());
        Product product = productRep.getReferenceById(productId);

        return product.getPrice() * quantity;
    }

    @Override
    public void removeProduct(Integer productId, User user) {
        orderBasketRep.deleteByUserAndProduct(user.getId(), productId);
    }

}

