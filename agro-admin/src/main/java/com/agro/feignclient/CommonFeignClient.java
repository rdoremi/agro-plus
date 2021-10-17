package com.agro.feignclient;

import com.agro.pojo.entity.Category;
import com.agro.result.Const;
import com.agro.result.ServerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(Const.Service.COMMONSERVICE)
public interface CommonFeignClient {
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
