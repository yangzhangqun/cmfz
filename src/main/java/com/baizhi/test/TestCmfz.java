package com.baizhi.test;

import com.baizhi.CmfzApplication;
import com.baizhi.dao.AdminDao;
import com.baizhi.entity.Admin;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = CmfzApplication.class)
public class TestCmfz {
    @Autowired
    AdminDao adminDao;
    @Test
    public void test1(){
        Admin admin = adminDao.findByUser("admin");
        System.out.println(admin);
    }
}
