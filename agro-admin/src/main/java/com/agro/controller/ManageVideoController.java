package com.agro.controller;



import com.agro.feignclient.VideoFeignClient;
import com.agro.result.ServerResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/manage/video")
public class ManageVideoController {

    @Autowired
    private VideoFeignClient videoFeignClient;

    @GetMapping("/get_no_check")
    public ServerResponse getNoChecklist(@RequestParam(value = "page",defaultValue = "1") int page, @RequestParam(value = "limit",defaultValue = "20") int limit){
        ServerResponse noCheckList = videoFeignClient.getNoChecklist(page, limit);
        return noCheckList;
    }

    @GetMapping("/get_check_list")
    public ServerResponse getChecklist(@RequestParam(value = "page",defaultValue = "1") Integer page, @RequestParam(value = "limit",defaultValue = "20") Integer limit,@RequestParam(value = "name",defaultValue = "") String name){
        System.out.println("page "+page);
        return videoFeignClient.getChecklist(page,limit,name);
    }
    @GetMapping("/get_nopass_list")
    public ServerResponse getNopasslist(@RequestParam(value = "page",defaultValue = "1") Integer page, @RequestParam(value = "limit",defaultValue = "20") Integer limit,@RequestParam(value = "name",defaultValue = "")String name){
        return videoFeignClient.getNopasslist(page,limit,name);
    }
    @GetMapping("/get_pass_list")
    public ServerResponse getAlllist(@RequestParam(value = "page",defaultValue = "1") Integer page, @RequestParam(value = "limit",defaultValue = "100") Integer limit,@RequestParam(value = "name",defaultValue = "")String name){
        return videoFeignClient.getAlllist(page,limit,name);
    }
}
