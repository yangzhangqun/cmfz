package com.baizhi.service;

import com.baizhi.entity.Album;

import java.util.Map;

public interface AlbumService {
    public Map<String,Object> showPage(Integer page, Integer rows);
    public   void    deleteAlbum(String[]  id);
    public    String   saveAlbum(Album album);
    public    void  updateAlbum(Album   album);
}
