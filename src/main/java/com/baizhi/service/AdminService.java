package com.baizhi.service;

import com.baizhi.entity.Admin;

import javax.servlet.http.HttpSession;
import java.util.Map;

public interface AdminService {
    //根据username查询管理员信息
    public Map<String,String> findByUser(Admin admin, String code, HttpSession session);
}
