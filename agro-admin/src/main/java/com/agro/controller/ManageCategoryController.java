package com.agro.controller;


import com.agro.feignclient.CommonFeignClient;
import com.agro.pojo.entity.Category;
import com.agro.result.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/manage/label")
public class ManageCategoryController {

    @Autowired
    private CommonFeignClient categoryFeignClient;

    @PostMapping("/add")
    public ServerResponse add(@RequestBody Category category){
        return categoryFeignClient.add(category);
    }
    @GetMapping("/getlist")
    public ServerResponse getList(){
        return categoryFeignClient.getList();
    }
    @GetMapping("/get")
    public ServerResponse get(@RequestParam(value = "name") String name){
        return categoryFeignClient.get(name);
    }
    @PostMapping("/update")
    public ServerResponse update( Category category){
        return categoryFeignClient.update(category);
    }
    @GetMapping("/delete")
    public ServerResponse delete(@RequestParam(value = "id")Integer id){
        return categoryFeignClient.delete(id);
    }
}
