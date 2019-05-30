package com.baizhi.controller;

import com.baizhi.util.ValidateImageCodeUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

@RequestMapping("/code")
@RestController
public class GetCode {
    @RequestMapping("code")
    public void getCode(HttpSession session, HttpServletResponse response){
        //获得验证码数字
        String code = ValidateImageCodeUtils.getSecurityCode();
        session.setAttribute("code",code);
        //获得图片格式的验证码
        BufferedImage image = ValidateImageCodeUtils.createImage(code);
        //用流的形式响应到前台页面

        ServletOutputStream outputStream = null;

        try {
            outputStream = response.getOutputStream();
            ImageIO.write(image, "png", outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
