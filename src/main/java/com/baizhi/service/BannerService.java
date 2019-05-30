package com.baizhi.service;

import com.baizhi.entity.Banner;

import java.util.Map;

public interface BannerService {
    //分页的Service  处理业务
    public Map<String,Object> showPage(Integer page, Integer rows);
    public   void    delete(String[]  id);
    public    String   save(Banner  banner);
    public   void   update(Banner  banner);
}
