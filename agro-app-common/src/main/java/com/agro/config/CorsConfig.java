package com.agro.config;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

//@Configuration
public class CorsConfig {

    public CorsConfig(){

    }
    //@Bean
    public CorsFilter corsFilter(){
        //添加cors的配置
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("http://localhost:8080/");//也可以写成 *

        //设置是否发送cookie信息
        corsConfiguration.setAllowCredentials(true);
        //设置允许请求的方式
        corsConfiguration.addAllowedMethod("*");
        //设置允许的header
        corsConfiguration.addAllowedHeader("*");

        //为url添加映射路径
        UrlBasedCorsConfigurationSource corsConfigurationSource = new UrlBasedCorsConfigurationSource();
        corsConfigurationSource.registerCorsConfiguration("/**",corsConfiguration);
        //返回重新定义好的corsFilter
        return new CorsFilter(corsConfigurationSource);

    }

}
