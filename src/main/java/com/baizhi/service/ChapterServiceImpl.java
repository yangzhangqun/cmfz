package com.baizhi.service;

import com.baizhi.dao.ChapterDao;
import com.baizhi.entity.Chapter;
import org.apache.commons.io.FileUtils;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;

@Service
@Transactional
public class ChapterServiceImpl implements ChapterService {
    @Autowired
    private ChapterDao  chapterDao;
    @Override
    public Map<String, Object> showPage(Integer page, Integer rows,String  album_id) {
        //准备返回客户端的数据
        Map<String,Object> maps = new HashMap<>();
        //当前页号
        maps.put("page",page);
        Integer totalCount =  chapterDao.selectTotalCount();
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
        List<Chapter> chapters = chapterDao.showAll(page, rows,album_id);
        maps.put("rows",chapters);
        return  maps;
    }

    @Override
    public void uploadChapter(MultipartFile audio, HttpSession session, String chapterId) {
        String realPath = session.getServletContext().getRealPath("/upload/audio");
        File  file=new File(realPath);
        if(!file.exists()){
            file.mkdirs();
        }
        String originalFilename = audio.getOriginalFilename();
        String name = new Date().getTime() + "_" + originalFilename;
        try {
            audio.transferTo(new File(realPath,name));
            AudioFile read = AudioFileIO.read(new File(realPath, name));
            AudioHeader audioHeader = read.getAudioHeader();
            int trackLength = audioHeader.getTrackLength();
            String seconds = trackLength % 60 + "秒";
            String minute = trackLength / 60 + "分";
            long l = audio.getSize();
            String size = l / 1024 / 1024 + "MB";
            Chapter   chapter=new Chapter();
            chapter.setId(chapterId);
            chapter.setDuration(minute+seconds);
            chapter.setSize(size);
            chapter.setAudio(name);
            chapter.setCreateTime(new Date());
            chapterDao.updateChapter(chapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String insertChapter(Chapter chapter) {
        chapter.setId(UUID.randomUUID().toString().replace("-",""));
        chapterDao.saveChapter(chapter);
        return chapter.getId();
    }

    @Override
    public void downLoadAudio(String audioName, HttpServletResponse response, HttpSession session) {
        String realPath = session.getServletContext().getRealPath("/upload/audio");
        File file = new File(realPath, audioName);
        String s = audioName.split("_")[1];
        try {
            String encode = URLEncoder.encode(s,"UTF-8");
            response.setHeader("content-disposition", "attachment;filename=" + encode);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        ServletOutputStream   outputStream=null;
        try {
            outputStream = response.getOutputStream();
            FileUtils.copyFile(file,outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
