package com.baizhi.service;

import com.baizhi.entity.Chapter;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

public interface ChapterService {
    public Map<String,Object> showPage(Integer page, Integer rows,String  album_id);
   public  void uploadChapter(MultipartFile audio, HttpSession session, String chapterId);
   public   String insertChapter(Chapter chapter);
     public  void downLoadAudio(String audioName, HttpServletResponse response, HttpSession session);
}
