package com.agro.api.controller;


import com.agro.pojo.dto.ArticleDto;
import com.agro.pojo.entity.Article;
import com.agro.result.Const;
import com.agro.result.ServerResponse;

import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fore/article")
//@FeignClient(Const.USERSERVICE)
public interface ArticeCotroller {



    @PostMapping("add")
    public ServerResponse add(Article article);

    @GetMapping("/get_list")
    public ServerResponse getList(@RequestParam(value = "page",defaultValue = "1") Integer page, @RequestParam(value = "limit",defaultValue = "10") Integer limit, String name);

    @GetMapping("/getlist")
    public ServerResponse gets(@RequestParam(value = "page",defaultValue = "1") Integer page, @RequestParam(value = "limit",defaultValue = "100") Integer limit,String name);

    @GetMapping("/getrecommend")
    public ServerResponse getRecommend();

    @GetMapping("/get")
    public ServerResponse article(String id,String userId);
    @GetMapping("/getByid")
    public ServerResponse getbyid(String id);
    @GetMapping("/delete")
    public ServerResponse delete(String id);
    @PostMapping("/update")
    public ServerResponse update(Article article);


}
