/*
package com.agro.feignclient;


import com.agro.pojo.entity.Article;
import com.agro.pojo.entity.Category;
import com.agro.result.Const;
import com.agro.result.ServerResponse;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@RequestMapping("/fore/article")
@FeignClient(value=Const.Service.APPSERVICE)
//@Component
public interface AppFeignClient {



   */
/* @PostMapping("add")
    public ServerResponse add(Article article);

    @GetMapping("/get_list")
    public ServerResponse getList(@RequestParam(value = "page",defaultValue = "1") Integer page, @RequestParam(value = "limit",defaultValue = "10") Integer limit, String name);

    @GetMapping("/getlist")
    public ServerResponse gets(@RequestParam(value = "page",defaultValue = "1") Integer page, @RequestParam(value = "limit",defaultValue = "100") Integer limit,String name);
*//*

    @GetMapping("/fore/article/getrecommend")
    public ServerResponse getRecommend();
    @GetMapping("/fore/article/get_no_check")
    public ServerResponse getNoCheck( @RequestParam(value = "page",defaultValue = "1") Integer page,
                                      @RequestParam(value = "limit",defaultValue = "100") Integer limit);

    @GetMapping("/fore/article/get_list")
    public ServerResponse getList(@RequestParam(value = "page",defaultValue = "1") Integer page, @RequestParam(value = "limit",defaultValue = "10") Integer limit, @RequestParam(value = "name")String name);

   */
/* @GetMapping("/get")
    public ServerResponse article(String id,String userId);
    @GetMapping("/getByid")
    public ServerResponse getbyid(String id);
    @GetMapping("/delete")
    public ServerResponse delete(String id);
    @PostMapping("/update")
    public ServerResponse update(Article article);
*//*

   @GetMapping("/video/get_no_check")
   public ServerResponse getNoChecklist(@RequestParam(value = "page",defaultValue = "1") int page, @RequestParam(value = "limit",defaultValue = "20") int limit);



    @GetMapping("/video/get_check_list")
    public ServerResponse getChecklist(@RequestParam(value = "page",defaultValue = "1") Integer page, @RequestParam(value = "limit",defaultValue = "20") Integer limit,@RequestParam(value = "name")String name);
    @GetMapping("/video/get_nopass_list")
    public ServerResponse getNopasslist(@RequestParam(value = "page",defaultValue = "1") Integer page, @RequestParam(value = "limit",defaultValue = "20") Integer limit,@RequestParam(value = "name")String name);
    @GetMapping("/video/get_pass_list")
    public ServerResponse getAlllist(@RequestParam(value = "page",defaultValue = "1") Integer page, @RequestParam(value = "limit",defaultValue = "100") Integer limit,@RequestParam(value = "name")String name);



    //分类
    @PostMapping("/label/add")
    public ServerResponse add(@RequestBody Category category);
    @GetMapping("/label/getlist")
    public ServerResponse getList();
    @GetMapping("/label/get")
    public ServerResponse get(@RequestParam(value = "name") String name);
    @PostMapping("/label/update")
    public ServerResponse update(@RequestBody Category category);
    @GetMapping("label/delete")
    public ServerResponse delete(@RequestParam(value = "id")Integer id);
}

*/
