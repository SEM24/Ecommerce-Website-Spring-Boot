package com.khomsi.site_project;

import com.khomsi.site_project.entity.OrderBasket;
import com.khomsi.site_project.entity.Product;
import com.khomsi.site_project.entity.User;
import com.khomsi.site_project.repository.OrderBasketRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class OrderBasketTests {
    @Autowired
    private OrderBasketRepository orderBasketRep;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testAddOneOrderBasketProduct() {
        Product product = entityManager.find(Product.class, 6);
        User user = entityManager.find(User.class, 8);

        OrderBasket newOrderBasket = new OrderBasket();

        newOrderBasket.setUser(user);
        newOrderBasket.setProduct(product);
        newOrderBasket.setQuantity(1);


        OrderBasket saveOrderBasketProd = orderBasketRep.save(newOrderBasket);

        assertTrue(saveOrderBasketProd.getId() > 0);
    }

    @Test
    public void testGetOrderBasketProdByUser() {
        User user = new User();
        user.setId(8);

        List<OrderBasket> orderBaskets = orderBasketRep.findByUser(user);

        assertEquals(1, orderBaskets.size());

    }

//    @Test
//    public String getProductsFromBasketsToOrder(List<OrderBasket> orderBaskets) {
//        Product product = entityManager.find(Product.class, 6);
//        User user = entityManager.find(User.class, 1);
//
//        OrderBasket newOrderBasket = new OrderBasket();
//
//        newOrderBasket.setUser(user);
//        newOrderBasket.setProduct(product);
//        newOrderBasket.setQuantity(1);
//
//        String result = null;
//        if (!orderBaskets.isEmpty()) {
//            for (OrderBasket orderBasket : orderBaskets) {
//                result += String.valueOf(orderBasket.getProduct().getId()) + "/";
//            }
//        }
//        return result;
//    }
}
