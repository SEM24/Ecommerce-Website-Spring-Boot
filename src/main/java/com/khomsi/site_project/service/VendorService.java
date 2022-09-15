package com.khomsi.site_project.service;

import com.khomsi.site_project.entity.Vendor;
import com.khomsi.site_project.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class VendorService implements IVendorService {
    @Autowired
    private VendorRepository vendorRepository;

    public static final int VENDORS_PER_PAGE = 4;

    @Override
    public List<Vendor> getAllVendors() {
        return vendorRepository.findAll();
    }

    @Override
    public void saveVendor(Vendor vendor) {
        vendorRepository.save(vendor);
    }

    @Override
    public Vendor getVendor(int id) {
        Vendor vendor = null;
        Optional<Vendor> optional = vendorRepository.findById(id);
        if (optional.isPresent()) {
            vendor = optional.get();
        }
        return vendor;
    }

    @Override
    public void deleteVendor(int id) {
        Long countById = vendorRepository.countById(id);
        if (countById == null || countById == 0) {
            throw new NotFoundException("Couldn't find any vendor with id " + id);
        }
        vendorRepository.deleteById(id);
    }

    @Override
    public Page<Vendor> listByPage(int pageNum) {
        Pageable pageable = PageRequest.of(pageNum - 1, VENDORS_PER_PAGE);
        return vendorRepository.findAll(pageable);
    }

    @Override
    public String checkVendorTitle(Integer id, String title) {
        Vendor vendor = vendorRepository.findByTitle(title);
        boolean isCreatingNew = (id == null);

        if (isCreatingNew) {
            if (vendor != null) return "Duplicate";
        } else {
            if (!Objects.equals(vendor.getId(), id)) {
                return "Duplicate";
            }
        }
        return "OK";
    }

}
