package com.baizhi.controller;

import com.baizhi.entity.Banner;
import com.baizhi.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.*;

@RestController
@RequestMapping("/banner")
public class BannerController {
    @Autowired
    private BannerService bannerService;
    @RequestMapping("/show")
    public Map<String,Object> showAllByPage(Integer page, Integer rows){
        Map<String, Object> map = bannerService.showPage(page, rows);
        return  map;
    }

    @RequestMapping("upload")
    public   String   upload(MultipartFile img_pic, HttpSession  session,String bannerId){
        String realPath = session.getServletContext().getRealPath("/upload/img");
        File file=new  File(realPath);
        if(!file.exists()){
            file.mkdirs();
        }
        String originalFilename = img_pic.getOriginalFilename();
        String s = new Date().getTime() + "_" + originalFilename;
        try {
            img_pic.transferTo(new File(realPath,s));
            Banner  banner=new Banner();
            banner.setId(bannerId);
            banner.setImg_pic(s);
            bannerService.update(banner);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return   null;
    }
    //增删改
    @RequestMapping("/edit")             //可以用对象接收对应的属性
    public   Map<String, String>  edit(String oper, Banner banner,String[] id,MultipartFile img_pic,HttpSession  session,String bannerId) throws  Exception{
        //判断是什么操作
        //添加操作
        Map<String, String> map = new HashMap<>();
        if(("add").equals(oper)){
            String s = bannerService.save(banner);
            map.put("msg","添加成功");
            map.put("bannerId",s);
            //修改操作
        }else  if (("edit").equals(oper)){
       if(banner.getImg_pic()==""||banner.getImg_pic()==null){
           bannerService.update(banner);
           map.put("id","");
       }else{
           bannerService.update(banner);
           map.put("id",banner.getId());
       }
            //删除操作
        }else if("del".equals(oper)){
            bannerService.delete(id);
            map.put("msg","删除成功");
            }
        return   map;
        }

    }


