package com.agro.controller;

import com.agro.pojo.entity.Collect;
import com.agro.pojo.entity.CourseVideo;
import com.agro.result.ServerResponse;
import com.agro.service.CollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/collect")
public class CollectController {

    @Autowired
    private CollectService collectService;


    @PostMapping("/add")
    public ServerResponse add(@RequestBody() Collect collect){
        System.out.println(collect);
        boolean add = collectService.add(collect);
        return add?ServerResponse.successMsg("收藏成功"):ServerResponse.errorMsg("收藏失败");
    }

    @GetMapping("/getlist")
    public ServerResponse getlist(String userId){
        System.out.println(userId);
        List<CourseVideo> collects = collectService.selectList(userId);
        return ServerResponse.success(collects);
    }

    @GetMapping("/delete")
    public ServerResponse delete(String userId,String productionId){
        boolean rs = collectService.deleteById(userId,productionId);
        return rs?ServerResponse.successMsg("删除成功"):ServerResponse.errorMsg("删除失败");
    }

    @GetMapping("/check_collect")
    public ServerResponse checkCollect(String productionId,String userId){
        boolean rs = collectService.checkCollect(productionId,userId);
        return rs?ServerResponse.successMsg("存在"):ServerResponse.errorMsg("不存在");
    }

}
