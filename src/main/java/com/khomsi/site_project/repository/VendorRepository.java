package com.khomsi.site_project.repository;

import com.khomsi.site_project.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VendorRepository extends JpaRepository<Vendor, Integer> {
    public Long countById(Integer id);

    @Query("SELECT v FROM Vendor v WHERE v.title = :title")
    public Vendor findByTitle(@Param("title") String title);
}
