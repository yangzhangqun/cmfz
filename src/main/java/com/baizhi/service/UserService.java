package com.baizhi.service;

import com.baizhi.entity.Province;
import com.baizhi.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    public Map<String,Object> showPage(Integer page, Integer rows);
    public   void   deleteUser(String[] id);
    public  String    saveUser(User   user);
    public   void   updateUser(User   user);
    public List<User>   getAll();
    public   User      find(String   id);
    public   Map<String, Object> queryTime();
    public   Map<String, Object>    getTime();
    public   List<Province>  getProvince();
}
