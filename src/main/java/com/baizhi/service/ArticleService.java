package com.baizhi.service;

import com.baizhi.entity.Article;

import java.util.Map;

public interface ArticleService {
    public Map<String,Object> showPage(Integer page, Integer rows);
    public    String   saveArticle(Article article);
    public   void    updateArticle(Article article);
    public   void    deleteArticle(String[] id);
}
