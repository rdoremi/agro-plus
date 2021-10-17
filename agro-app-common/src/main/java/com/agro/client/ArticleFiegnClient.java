package com.agro.client;


import com.agro.result.Const;
import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(Const.Service.ARTICLESERVICE)
public interface ArticleFiegnClient {

}
