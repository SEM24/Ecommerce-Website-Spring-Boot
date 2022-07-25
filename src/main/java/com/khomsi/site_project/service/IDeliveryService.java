package com.khomsi.site_project.service;

import com.khomsi.site_project.entity.Delivery;

import java.util.List;

public interface IDeliveryService {
    public List<Delivery> getAllDeliveries();

    public void saveDelivery(Delivery delivery);

    public Delivery getDelivery(int id);

    public void deleteDelivery(int id);
}
