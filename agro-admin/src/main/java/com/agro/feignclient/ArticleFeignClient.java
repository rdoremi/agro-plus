package com.agro.feignclient;

import com.agro.result.Const;
import com.agro.result.ServerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(Const.Service.ARTICLESERVICE)
public interface ArticleFeignClient {
    @GetMapping("/fore/article/getrecommend")
    public ServerResponse getRecommend();
    @GetMapping("/fore/article/get_no_check")
    public ServerResponse getNoCheck( @RequestParam(value = "page",defaultValue = "1") Integer page,
                                      @RequestParam(value = "limit",defaultValue = "100") Integer limit);

    @GetMapping("/fore/article/get_list")
    public ServerResponse getList(@RequestParam(value = "page",defaultValue = "1") Integer page, @RequestParam(value = "limit",defaultValue = "10") Integer limit, @RequestParam(value = "name")String name);

}
