package com.agro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/fore")
public class IndexController {



    @GetMapping("/home")
    public String home(){
        return "fore/home";
    }
    @GetMapping("/login")
    public String login(){
        return "fore/login";
    }
    @GetMapping("/register")
    public String register(){
        return "fore/register";
    }

    @GetMapping("/upload")
    public String upload(){
        return "fore/page/video-publish";
    }

    @GetMapping("/video/{id}")
    public ModelAndView video(@PathVariable("id") Long id, ModelAndView modelAndView){

        //servletRequest.setAttribute("id",id);
        modelAndView.addObject("id",id);
        modelAndView.setViewName("fore/page/video");

        return modelAndView;
    }
    @GetMapping("/editor")
    public String editor(){
        return "fore/page/editor";
    }
    @GetMapping("/publish_article")
    public String publishArticle(){
        return "fore/page/article-publish";
    }

    @GetMapping("/detail/{id}")
    public ModelAndView articleDetail(@PathVariable("id")String id,ModelAndView modelAndView){

        modelAndView.addObject("id",id);
        modelAndView.setViewName("fore/page/article-detail");
        return modelAndView;
    }

    @GetMapping("/chat")
    public String chat(){
        return "fore/chat";
    }
    @GetMapping("/chat1")
    public String chat1(){
        return "fore/chat1";
    }
}
