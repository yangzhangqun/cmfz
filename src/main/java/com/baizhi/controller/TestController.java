package com.baizhi.controller;

import com.baizhi.service.AlbumService;
import com.baizhi.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private BannerService  bannerService;
    @Autowired
    private AlbumService  albumService;
    @RequestMapping("first_page")
    public Map<String, Object> first_page(String all, String wen, String si, String ssyj, String xmfy) {
        Map<String, Object> map1 = bannerService.showPage(1, 3);
        Map<String, Object> map2 = albumService.showPage(1, 3);
        Map<String, Object> map = new HashMap<String, Object>();
        if (all == null || si == null || ssyj == null) {
            //返回错误信息  错误码  与  错误信息
        } else {
            if (all != null) {
                map.put("banner", map1);
                map.put("album", map2);
                map.put("article", "你的上师对你发布的文章集合");
            } else if (wen != null) {
                map.put("album", "专辑集合");
            }else if(si != null){
                if(ssyj != null){
                    map.put("article", "你的上师对你发布的文章集合");
                }else if(xmfy != null){
                    map.put("article", "所有上师的文章集合");
                }
            }
        }
        return map;
    }
}

