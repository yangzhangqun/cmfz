package com.baizhi.dao;

import com.baizhi.entity.Album;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AlbumDao {
    //根据id删除专辑
    public void deleteAlbum(String[] id);
    //修改专辑
    public   void   updateAlbum(Album   album);
    //查询所有
    public List<Album>   showAll(@Param("page") Integer page, @Param("rows") Integer rows);
    //查询总条数
    public  Integer  selectTotalCount();
    //添加数据
    public   void  savebAlbum(Album  album);
    //根据id查询单个
    public Album      findById(String   id);
     public Album     delete(String   id);

      public Album     update(String   id);
}
