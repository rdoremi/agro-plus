package com.agro.client;


import com.agro.pojo.entity.CourseVideo;
import com.agro.result.Const;
import com.agro.result.ServerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(Const.Service.VIDEOSERVICE)
public interface VideoFiegnClient {

    @GetMapping("/video/videoInfo")
    ServerResponse getVideoInfo(@RequestParam("productionId") String productionId);
}
