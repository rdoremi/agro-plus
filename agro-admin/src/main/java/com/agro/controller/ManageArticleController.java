package com.agro.controller;


import com.agro.feignclient.ArticleFeignClient;
import com.agro.result.ServerResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/manage/article")
public class ManageArticleController {

    @Autowired
    private ArticleFeignClient articleFeignClient;
   /* @Autowired
    private ArticleService articleService;
*/
    @GetMapping("/get_no_check")
    public ServerResponse getNoChecklist(@RequestParam(value = "page",defaultValue = "1") int page, @RequestParam(value = "limit",defaultValue = "20") int limit){
        ServerResponse noCheckList = articleFeignClient.getNoCheck(page,limit);
        return noCheckList;
    }
    @GetMapping("/get_list")
    public ServerResponse getList(@RequestParam(value = "page",defaultValue = "1") Integer page, @RequestParam(value = "limit",defaultValue = "10") Integer limit, @RequestParam(value = "name",defaultValue = "")String name){
        return articleFeignClient.getList(page, limit, name);
    }


}
