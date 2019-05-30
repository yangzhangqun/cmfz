package com.baizhi.dao;

import com.baizhi.entity.Month;
import com.baizhi.entity.Province;
import com.baizhi.entity.User;
import com.baizhi.entity.qi;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {
    public List<User> showAll(@Param("page") Integer page, @Param("rows") Integer rows);
    public  Integer  selectTotalCount();
    public    void   deleteUser(String[]  id);
    public  void     saveUser(User   user);
    public   void   updateUser(User  user);
    public    List<User>   getAll();
    public    User     findById(String   id);
    public    List<qi>  queryTime();
    public   List<Month>    getTime();
    public   List<Province>    getProvince();
}
