package com.baizhi.controller;

import com.baizhi.entity.Article;
import com.baizhi.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService  articleService;
    @RequestMapping("/showAllPage")
    public Map<String, Object> showAllByPage(Integer page, Integer rows) {
        Map<String, Object> map = articleService.showPage(page, rows);
        return map;
    }
    @RequestMapping("edit")
    public   void     edit(Article article){
        articleService.saveArticle(article);
    }
    @RequestMapping("delete")
    public   void    delete(String[] id){
        articleService.deleteArticle(id);
    }
    @RequestMapping("update")
    public   void    update(Article  article){
        articleService.updateArticle(article);
    }

}
