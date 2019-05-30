package com.baizhi.dao;

import com.baizhi.entity.Admin;

public interface AdminDao {
    //根据username查询管理员信息
    public Admin findByUser(String username);
}
