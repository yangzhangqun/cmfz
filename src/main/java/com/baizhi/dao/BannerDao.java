package com.baizhi.dao;

import com.baizhi.entity.Banner;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BannerDao {
    //查询所有轮播图数据
    public List<Banner>    showAll(@Param("page") Integer page, @Param("rows") Integer rows);
    //查询总条数
    public  Integer  selectTotalCount();
    //根据id查询单个
    public  Banner     findById(String id);
    //根据id删除
    public   void     delete(String[] id);
    //添加一条数据
    public   void     save(Banner   banner);
    //修改id修改
    public  void     update(Banner   banner);
}
