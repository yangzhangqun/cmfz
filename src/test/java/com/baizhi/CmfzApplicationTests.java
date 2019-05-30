package com.baizhi;

import com.baizhi.entity.Province;
import com.baizhi.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CmfzApplicationTests {
@Autowired
private UserService   userService;
@Test
   public void  insert(){
    List<Province> province = userService.getProvince();
    for (Province province1 : province) {
        System.out.println(province1);
    }



}


}



