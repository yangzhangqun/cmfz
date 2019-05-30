package com.baizhi.service;


import com.baizhi.dao.AlbumDao;
import com.baizhi.entity.Album;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
public class AlbumServiceImpl implements AlbumService {
    @Autowired
    private AlbumDao  albumDao;
    @Override
    public Map<String, Object> showPage(Integer page, Integer rows) {
        //准备返回客户端的数据
        Map<String,Object> maps = new HashMap<>();
        //当前页号
        maps.put("page",page);
        Integer totalCount = albumDao.selectTotalCount();
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
        List<Album> albums = albumDao.showAll(page, rows);
        maps.put("rows",albums);
        return  maps;
    }

    @Override
    public void deleteAlbum(String[] id) {
        albumDao.deleteAlbum(id);
    }

    @Override
    public String saveAlbum(Album album) {
        album.setId(UUID.randomUUID().toString().replace("-",""));
        album.setPublicTime(new Date());
        albumDao.savebAlbum(album);
        return album.getId();
    }

    @Override
    public void updateAlbum(Album album) {
        String id = album.getId();
        if(album.getCoverImg()==""||album.getCoverImg()==null){
            Album byId = albumDao.findById(id);
            album.setCoverImg(byId.getCoverImg());
        }
        albumDao.updateAlbum(album);
    }

}
