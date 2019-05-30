package com.baizhi.service;

import com.baizhi.dao.BannerDao;
import com.baizhi.entity.Banner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class BannerServiceImpl implements BannerService {
    @Autowired
   private  BannerDao   bannerDao;
    @Override
    public Map<String, Object> showPage(Integer page, Integer rows) {
        //准备返回客户端的数据
        Map<String,Object> maps = new HashMap<>();
        //当前页号
        maps.put("page",page);
        Integer totalCount =  bannerDao.selectTotalCount();
        //总条数
        maps.put("records",totalCount);
        //总页数
        Integer  pageCount=0;
        //总页数
        if(totalCount%rows!=0){
            pageCount=totalCount/rows+1;
        }else{
            pageCount=totalCount/rows;
        }
        maps.put("total",pageCount);
        //当前数据内容
        List<Banner> banners = bannerDao.showAll(page, rows);
        maps.put("rows",banners);
        return  maps;
    }

    @Override
    public void delete(String[] id) {
        bannerDao.delete(id);
    }

    @Override
    public String save(Banner banner) {
        banner.setId(UUID.randomUUID().toString().replace("-", ""));
        banner.setCreate_date(new Date());
        bannerDao.save(banner);
        return    banner.getId();
    }

    @Override
    public void update(Banner banner) {
        String id=banner.getId();
        if (banner.getImg_pic()==""||banner.getImg_pic()==null){
            Banner byId = bannerDao.findById(id);
            banner.setImg_pic(byId.getImg_pic());
        }
        bannerDao.update(banner);
    }
}
