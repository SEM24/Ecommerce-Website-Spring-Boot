package com.khomsi.site_project.service;

import com.khomsi.site_project.entity.UserInfo;

import java.util.List;

public interface IUserInfoService {

    public List<UserInfo> getAllUserDetails();

    public void saveUserDetail(UserInfo userInfo);

    public UserInfo getUserDetail(int id);

    public void deleteUserDetail(int id);
}
