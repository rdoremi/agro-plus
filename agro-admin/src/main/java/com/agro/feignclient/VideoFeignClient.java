package com.agro.feignclient;


import com.agro.result.Const;
import com.agro.result.ServerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/video")
@FeignClient(value = Const.Service.VIDEOSERVICE)
public interface VideoFeignClient {


    @GetMapping("/get_no_check")
    public ServerResponse getNoChecklist(@RequestParam(value = "page", defaultValue = "1") int page, @RequestParam(value = "limit", defaultValue = "20") int limit);


    @GetMapping("/get_check_list")
    public ServerResponse getChecklist(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "limit", defaultValue = "20") Integer limit, @RequestParam(value = "name") String name);

    @GetMapping("/get_nopass_list")
    public ServerResponse getNopasslist(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "limit", defaultValue = "20") Integer limit, @RequestParam(value = "name") String name);

    @GetMapping("/get_pass_list")
    public ServerResponse getAlllist(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "limit", defaultValue = "100") Integer limit, @RequestParam(value = "name") String name);


}