package com.baizhi.controller;
import com.baizhi.entity.Chapter;
import com.baizhi.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/chapter")
public class ChapterController {
    @Autowired
    private ChapterService chapterService;

    @RequestMapping("/show")
    public Map<String, Object> showAllByPage(Integer page, Integer rows,String  album_id) {
        Map<String, Object> map = chapterService.showPage(page, rows,album_id);
        return map;
    }
    @RequestMapping("downLoadAudio")
    public void  downLoadAudio(String audioName, HttpServletResponse response, HttpSession session)throws    Exception{
  chapterService.downLoadAudio(audioName,response,session);
    }

    @RequestMapping("editChapter")
    public Map<String,String> editChapter(Chapter chapter, String oper){
        Map<String, String> map = new HashMap<>();
        if (oper.equals("add")){
            String s = chapterService.insertChapter(chapter);
            map.put("chapterId",s);
        }
        return map;
    }
    @RequestMapping("upload")
    public Map<String,String> upload(MultipartFile audio, HttpSession session, String chapterId) {
        chapterService.uploadChapter(audio,session,chapterId);
        return null;
    }
}
