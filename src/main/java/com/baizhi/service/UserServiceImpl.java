package com.baizhi.service;

import com.baizhi.dao.UserDao;
import com.baizhi.entity.Month;
import com.baizhi.entity.Province;
import com.baizhi.entity.User;
import com.baizhi.entity.qi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public Map<String, Object> showPage(Integer page, Integer rows) {
        //准备返回客户端的数据
        Map<String, Object> maps = new HashMap<>();
        //当前页号
        maps.put("page", page);
        Integer totalCount = userDao.selectTotalCount();
        //总条数
        maps.put("records", totalCount);
        //总页数
        Integer pageCount = 0;
        //总页数
        if (totalCount % rows != 0) {
            pageCount = totalCount / rows + 1;
        } else {
            pageCount = totalCount / rows;
        }
        maps.put("total", pageCount);
        //当前数据内容
        List<User> users = userDao.showAll(page, rows);
        maps.put("rows", users);
        return maps;
    }

    @Override
    public void deleteUser(String[] id) {
        userDao.deleteUser(id);
    }

    @Override
    public String saveUser(User user) {
        user.setId(UUID.randomUUID().toString().replace("-", ""));
        user.setSalt(null);
        user.setCreate_date(new Date());
        userDao.saveUser(user);
        return user.getId();
    }

    @Override
    public void updateUser(User user) {
        userDao.updateUser(user);

    }

    @Override
    public List<User> getAll() {
        List<User> all = userDao.getAll();
        return all;

    }

    @Override
    public User find(String id) {
        User user = userDao.findById(id);
        return user;
    }
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Map<String, Object> queryTime() {
        Map<String, Object> map = new HashMap<>();
        List<qi> q = userDao.queryTime();
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList1 = new ArrayList();
        for (qi qi : q) {
            arrayList.add(qi.getClick_date().getDate()+"日");
            arrayList1.add(qi.getCount());
        }
        map.put("date",arrayList);
        map.put("count", arrayList1);

        return map;
    }

    @Override
    public Map<String, Object> getTime() {
        Map<String, Object> map = new HashMap<>();
        List<Month> m = userDao.getTime();
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList1 = new ArrayList();
        for (Month month : m) {
            arrayList.add(month.getYue()+"月");
            arrayList1.add(month.getCount());
        }
      map.put("date",arrayList);
        map.put("count",arrayList1);
        return map;
    }

    @Override
    public List<Province> getProvince() {
        List<Province> province = userDao.getProvince();
        return province;
    }


}
