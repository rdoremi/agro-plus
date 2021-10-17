package com.agro.controller;


import com.agro.pojo.dto.ArticleDto;
import com.agro.pojo.entity.Article;
import com.agro.result.ServerResponse;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/fore/article")
@Slf4j
public class ArticeCotroller {

    private static String VIEWCOUNT = "view_count:";

    @Autowired
    private ArticleService articleService;

    @Autowired
    private RedisTemplate redisTemplate;

    @PostMapping("add")
    public ServerResponse add(Article article){


        Article rs = articleService.insert(article);
        return rs==null?ServerResponse.errorMsg("发布失败"):ServerResponse.successMsg("发布成功");
    }

    @GetMapping("/get_list")
    public ServerResponse getList(@RequestParam(value = "page",defaultValue = "1") Integer page, @RequestParam(value = "limit",defaultValue = "10") Integer limit, String name){

        if (!StringUtils.isEmpty(name)){
            PageInfo list = articleService.getListByName(page, limit,name);
            return ServerResponse.success(list);
        }
        PageInfo list = articleService.getList(page, limit);
        return ServerResponse.success(list);
    }

    @GetMapping("/getlist")
    public ServerResponse gets(@RequestParam(value = "page",defaultValue = "1") Integer page, @RequestParam(value = "limit",defaultValue = "100") Integer limit,String name){

        if (!StringUtils.isEmpty(name)){
            PageInfo list = articleService.getListByName(page, limit,name);
            return ServerResponse.success(list);
        }
        PageInfo list = articleService.getList(page, limit);
        return ServerResponse.success(list);
    }

    @GetMapping("/getrecommend")
    public ServerResponse getRecommend(){
        List<ArticleDto> rs = articleService.getRecommend();
        return ServerResponse.success(rs);
    }

    @GetMapping("/get")
    public ServerResponse article(String id,String userId){

        redisTemplate.opsForSet().add(VIEWCOUNT+id,userId);
        ArticleDto articleDto = articleService.selectById(id);
        Long size = redisTemplate.opsForSet().size(VIEWCOUNT + id);
        articleDto.setViewCount(size);
        return ServerResponse.success(articleDto);
    }
    @GetMapping("/getByid")
    public ServerResponse getbyid(String id){


        ArticleDto articleDto = articleService.selectById(id);

        return ServerResponse.success(articleDto);
    }
    @GetMapping("/delete")
    public ServerResponse delete(String id){
        boolean b = articleService.deleteById(id);
        return b?ServerResponse.successMsg("删除成功"):ServerResponse.errorMsg("删除失败");
    }
    @PostMapping("/update")
    public ServerResponse update(Article article){
        System.out.println(article);
        boolean rs = articleService.update(article);
        return rs?ServerResponse.successMsg("修改成功"):ServerResponse.errorMsg("修改失败");
    }
    @GetMapping("/get_no_check")
    public ServerResponse getNoCheck(@RequestParam(value = "page",defaultValue = "1") Integer page, @RequestParam(value = "limit",defaultValue = "100") Integer limit){
        PageInfo noCheckList = articleService.getList(page,limit);
        return ServerResponse.success(noCheckList);
    }




}
