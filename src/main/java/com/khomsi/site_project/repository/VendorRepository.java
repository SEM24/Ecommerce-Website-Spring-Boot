package com.khomsi.site_project.repository;

import com.khomsi.site_project.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorRepository extends JpaRepository<Vendor, Integer> {
}
