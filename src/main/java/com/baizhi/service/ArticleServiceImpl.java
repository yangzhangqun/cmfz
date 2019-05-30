package com.baizhi.service;

import com.baizhi.dao.ArticleDao;
import com.baizhi.entity.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleDao articleDao;
    @Override
    public Map<String, Object> showPage(Integer page, Integer rows) {
        //准备返回客户端的数据
        Map<String,Object> maps = new HashMap<>();
        //当前页号
        maps.put("page",page);
        Integer totalCount = articleDao.selectTotalCount();
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
        List<Article> articles = articleDao.showAll(page, rows);
        maps.put("rows",articles);
        return  maps;
    }

    @Override
    public String saveArticle(Article article) {
        article.setId(UUID.randomUUID().toString().replace("-",""));
        article.setCreate_date(new Date());
        article.setGuru_id("1");
        articleDao.save(article);
        return article.getId();
    }

    @Override
    public void updateArticle(Article article) {
       articleDao.update(article);
    }

    @Override
    public void deleteArticle(String[] id) {
         articleDao.delete(id);
    }

}
