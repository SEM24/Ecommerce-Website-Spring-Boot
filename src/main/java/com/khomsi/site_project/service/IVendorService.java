package com.khomsi.site_project.service;

import com.khomsi.site_project.entity.Vendor;

import java.util.List;

public interface IVendorService {
    public List<Vendor> getAllVendors();

    public void saveVendor(Vendor vendor);

    public Vendor getVendor(int id);

    public void deleteVendor(int id);
}
