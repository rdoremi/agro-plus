package com.agro.client;

import com.agro.pojo.entity.User;
import com.agro.result.Const;
import com.agro.result.ServerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(Const.Service.USERSERVICE)
public interface UserFeignClient {

    @GetMapping("/fore/user/userInfo")
    ServerResponse getUserInfo(@RequestParam("userId")String userId);
}
