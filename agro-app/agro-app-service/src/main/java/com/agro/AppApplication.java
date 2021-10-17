package com.agro;

import com.agro.utils.SpringUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.agro.mapper")
//@ComponentScan(basePackages = {"com.agro"})
@EnableCaching
//@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableEurekaClient
@EnableFeignClients({"com.agro"})
public class AppApplication {

    @Bean
    public SpringUtil getSpringUtil(){
        return new SpringUtil();
    }
    public static void main(String[] args) {
        SpringApplication.run(AppApplication.class,args);
    }
}
