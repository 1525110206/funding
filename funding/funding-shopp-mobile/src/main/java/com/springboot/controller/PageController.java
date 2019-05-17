package com.springboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class PageController {

    @RequestMapping({"/","/index.html"})
    public String index(){
        return "index";
    }

    @RequestMapping({"/login.html"})
    public String login(){
        return "/login";
    }

    @RequestMapping({"/reg.html"})
    public String reg(){
        return "/reg";
    }


    @RequestMapping({"/main.html"})
    public String main(){
        return "main";
    }

    @RequestMapping({"/member.html"})
    public String member(){
        return "member";
    }


}
