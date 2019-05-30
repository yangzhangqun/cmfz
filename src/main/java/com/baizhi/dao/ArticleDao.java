package com.baizhi.dao;

import com.baizhi.entity.Article;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleDao {
    public List<Article> showAll(@Param("page") Integer page, @Param("rows") Integer rows);
    public  Integer  selectTotalCount();
    public   void   save(Article  article);
    public   void   delete(String[] id);
    public   void    update(Article article);
}
