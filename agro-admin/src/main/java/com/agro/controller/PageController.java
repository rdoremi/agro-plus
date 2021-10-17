package com.agro.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
//@RequestMapping("")
public class PageController {

    private static final String BASE_PAGE_URL = "admin/page/";

    @GetMapping("/index")
    public String index(){
        return "admin/index";
    }
    @GetMapping("/login")
    public String login(){
        return "admin/login";
    }

    @GetMapping("/{url}")
    public String toPage(@PathVariable("url") String url){
        return BASE_PAGE_URL+url;
    }

}
