package com.baizhi.controller;

import org.apache.commons.io.FilenameUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/kindeditor")
public class KindeditorController {
    @RequestMapping("uploadImg")
    public Map<String, Object> uploadImg(HttpServletRequest request, MultipartFile img) throws Exception {
        Map<String, Object> map = new HashMap<>();
        String realPath = request.getSession().getServletContext().getRealPath("/upload/img");
        File file = new File("realPath");
        if (!file.exists()) {
            file.mkdirs();
        }
        String originalFilename = img.getOriginalFilename();
        String s = new Date().getTime() + "_" + originalFilename;
        img.transferTo(new File(realPath, s));


        map.put("error", 0);
        String scheme = request.getScheme();//http
        InetAddress localHost = InetAddress.getLocalHost();//localhost
        String s1 = localHost.toString();
        String localhost = s1.split("/")[1];

        int serverPort = request.getServerPort();
        String servletPath = request.getServletPath();
        String url = scheme + "://" + localhost + ":" + serverPort + servletPath + "/upload/img/" + s;
        map.put("url", url);
        return map;
    }
    @RequestMapping("getAll")
    public Map<String, Object> getAll(HttpServletRequest request) throws IOException {
        Map<String, Object> map = new HashMap<>();
        String realPath = request.getSession().getServletContext().getRealPath("/upload/img");
        File file = new File(realPath);
        ArrayList<Object> list = new ArrayList<>();
        String[] imgs = file.list();//所有的图片对象
        for (String img : imgs) {
            HashMap<String, Object> ma = new HashMap<>();
            ma.put("is_dir", false);
            ma.put("has_file", false);
            // http://localhost:9999/cmfz/upload/img/lunbuto.png
            File file1 = new File(realPath,img);
            long length = file1.length();
            ma.put("filesize", length);//图片的大小
            ma.put("dir_path", "");
            ma.put("is_photo", true);
            String extension = FilenameUtils.getExtension(img);
            ma.put("filetype", extension);//图片的格式  jpg | png | ...
            ma.put("filename", img);
            String s = img.split("_")[0];//时间戳
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            long lt = new Long(s);
            Date date = new Date(lt);
            String format = simpleDateFormat.format(date);
            ma.put("datetime", format);//字符串的时间
            list.add(ma);
        }
        map.put("file_list", list);
        map.put("moveup_dir_path", "");
        String scheme = request.getScheme();// http
        InetAddress localHost = InetAddress.getLocalHost(); //localhost
        String s1 = localHost.toString();
        String localhost = s1.split("/")[1];

        int serverPort = request.getServerPort();//9999
        String contextPath = request.getContextPath(); // /cmfz
        String url = scheme + "://" + localhost + ":" + serverPort + contextPath + "/upload/img/";
        map.put("current_url", url); //http://localhost:9999/cmfz/upload/img/
        int length = imgs.length;
        map.put("total_count",length); //图片的总数量
        return map;
    }

}
