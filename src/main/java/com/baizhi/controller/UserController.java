package com.baizhi.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baizhi.entity.Province;
import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping("/showAllPage")
    public Map<String, Object> showAllByPage(Integer page, Integer rows) {
        Map<String, Object> map = userService.showPage(page, rows);
        return map;
    }

    @RequestMapping("/edit")             //可以用对象接收对应的属性
    public Map<String, String> edit(String oper, User user, String[] id, HttpSession session) throws Exception {
        //判断是什么操作
        //添加操作
        Map<String, String> map = new HashMap<>();
        if (("add").equals(oper)) {
            String s = userService.saveUser(user);
            map.put("msg", "添加成功");
            map.put("userId", s);
            //修改操作
        } else if (("edit").equals(oper)) {
            //删除操作
        } else if ("del".equals(oper)) {
            userService.deleteUser(id);
            map.put("msg", "删除成功");
        }
        return map;
    }

    @RequestMapping("upload")
    public String upload(MultipartFile head_pic, HttpSession session, String userId) {
        String realPath = session.getServletContext().getRealPath("/upload/img");
        File file = new File(realPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        String originalFilename = head_pic.getOriginalFilename();
        String s = new Date().getTime() + "_" + originalFilename;
        try {
            head_pic.transferTo(new File(realPath, s));
            User user = new User();
            user.setId(userId);
            user.setHead_pic(s);
            userService.updateUser(user);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("getAll")
    public void getAll(HttpSession session, HttpServletResponse response) throws Exception {
        String realPath = session.getServletContext().getRealPath("/upload/img/");
        List<User> list = userService.getAll();
        for (User user : list) {
            user.setHead_pic(realPath + user.getHead_pic());
        }
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("女神表", "女神信息表"),
                User.class, list);
        String encode = URLEncoder.encode("女神.xls", "utf-8");
        response.setHeader("content-disposition", "attachment;filename=" + encode);
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
    }

    @RequestMapping("updateStatus")
    public Map<String, String> updateStatus(User user, String id) {
        Map<String, String> map = new HashMap<>();
        User user1 = userService.find(id);
        if (user1.getStatus().equals("正常")) {
            user1.setStatus("冻结");
        } else {
            user1.setStatus("正常");
        }
        userService.updateUser(user1);
        map.put("msg", "修改成功");
        return map;
    }

    @RequestMapping("queryTime")
    public List queryTime() {
        ArrayList<Object> objects = new ArrayList<>();
        Map<String, Object> map = userService.queryTime();
        objects.add(map);
        return objects;
    }

    @RequestMapping("getTime")
    public List getTime() {
        ArrayList<Object> objects = new ArrayList<>();
        Map<String, Object> map = userService.getTime();
        objects.add(map);
        return objects;

    }

    @RequestMapping("getProvince")
    public List<Province> getProvince() {
        List<Province> province = userService.getProvince();
        return province;
    }

}
