package com.baizhi.controller;

import com.baizhi.entity.Album;
import com.baizhi.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/album")
public class AlbumController {
    @Autowired
    private AlbumService albumService;

    @RequestMapping("/get")
    public Map<String, Object> showAllByPage(Integer page, Integer rows) {
        Map<String, Object> map = albumService.showPage(page, rows);
        return map;
    }
    @RequestMapping("/edit")             //可以用对象接收对应的属性
    public   Map<String, String>  edit(String oper, Album  album, String[] id, MultipartFile coverImg, HttpSession session, String albumId) throws  Exception{
        //判断是什么操作
        //添加操作
        Map<String, String> map = new HashMap<>();
        if(("add").equals(oper)){
            String s = albumService.saveAlbum(album);
            map.put("msg","添加成功");
            map.put("albumId",s);
            //修改操作
        }else  if (("edit").equals(oper)){
           if(album.getCoverImg()==""||album.getCoverImg()==null){
               albumService.updateAlbum(album);
               map.put("id","");
           }else{
               albumService.updateAlbum(album);
               map.put("id",album.getId());
           }
            //删除操作
        }else if("del".equals(oper)){
            albumService.deleteAlbum(id);
            map.put("msg","删除成功");
        }
        return   map;
    }
    @RequestMapping("upload")
    public   String   upload(MultipartFile coverImg, HttpSession  session,String albumId){
        String realPath = session.getServletContext().getRealPath("/upload/img");
        File file=new  File(realPath);
        if(!file.exists()){
            file.mkdirs();
        }
        String originalFilename = coverImg.getOriginalFilename();
        String s = new Date().getTime() + "_" + originalFilename;
        try {
            coverImg.transferTo(new File(realPath,s));
            Album  album=new Album();
            album.setId(albumId);
            album.setCoverImg(s);
            albumService.updateAlbum(album);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return   null;
    }

}
