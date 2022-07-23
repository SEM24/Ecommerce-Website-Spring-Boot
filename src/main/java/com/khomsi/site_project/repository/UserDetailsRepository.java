package com.khomsi.site_project.repository;

import com.khomsi.site_project.entity.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailsRepository extends JpaRepository<UserDetails, Integer> {
}
