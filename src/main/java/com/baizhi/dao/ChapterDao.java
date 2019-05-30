package com.baizhi.dao;

import com.baizhi.entity.Chapter;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ChapterDao {
    public List<Chapter> showAll(@Param("page") Integer page, @Param("rows") Integer rows,@Param("album_id")String   album_id);
    //查询总条数
    public  Integer  selectTotalCount();
    public   void     updateChapter(Chapter  chapter);
    public   void    saveChapter(Chapter  chapter);
}
