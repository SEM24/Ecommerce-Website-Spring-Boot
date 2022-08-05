package com.khomsi.site_project.repository;

import com.khomsi.site_project.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {
}
