package com.baizhi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/edit")
public class EditController {
    @RequestMapping("/over")
    public   String    over(HttpSession session){
        session.invalidate();
        return   "/login/login";
    }
}
