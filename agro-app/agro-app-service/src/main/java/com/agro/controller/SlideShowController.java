package com.agro.controller;

import com.agro.pojo.entity.Slideshow;
import com.agro.result.ServerResponse;
import com.agro.service.SlideShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/slideshow")
public class SlideShowController {

    @Autowired
    private SlideShowService slideShowService;

    @PostMapping("/add")
    public ServerResponse add(Slideshow slideshow){
        int rs = slideShowService.add(slideshow);
        return rs>0?ServerResponse.successMsg("添加成功"):ServerResponse.errorMsg("添加失败");
    }

    @GetMapping("/getlist")
    public ServerResponse getlist(){
        List<Slideshow> slideshows = slideShowService.selectAll();
        return ServerResponse.success(slideshows);
    }
    @GetMapping("/delete")
    public ServerResponse delete(String id){
        boolean rs = slideShowService.delete(id);
        return rs?ServerResponse.successMsg("删除成功"):ServerResponse.errorMsg("删除失败");
    }

}
