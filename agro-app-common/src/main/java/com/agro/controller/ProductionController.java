package com.agro.controller;

import com.agro.pojo.vo.ProductionInfo;
import com.agro.result.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/production")
public class ProductionController {

    private static String LIKE = "production:like:";
    private static String DISLIKE = "production:dislike:";

    @Autowired
    private RedisTemplate redisTemplate;


    @PostMapping("/like")
    public ServerResponse like(String productionId,String userId){
        String likeKey = LIKE+productionId;
        String disKey = DISLIKE+productionId;

        return likeOrDisLike(userId,disKey,likeKey);
    }
    @PostMapping("/dislike")
    public ServerResponse dislike(String productionId,String userId){
        String likeKey = LIKE+productionId;
        String disKey = DISLIKE+productionId;

        return likeOrDisLike(userId,likeKey,disKey);
    }

    private ServerResponse likeOrDisLike(String userId,String checkKey,String goalKey){

        Boolean likeMember = redisTemplate.opsForSet().isMember(checkKey, userId);
        if (likeMember){
            redisTemplate.opsForSet().remove(checkKey,userId);

        }
        redisTemplate.opsForSet().add(goalKey,userId);
        return ServerResponse.successMsg("ok");
    }

    @GetMapping("/getInfo")
    public ServerResponse getInfo(String productionId,String userId){
        String likeKey = LIKE+productionId;
        String disKey = DISLIKE+productionId;
        Long like = redisTemplate.opsForSet().size(likeKey);
        Long dislike = redisTemplate.opsForSet().size(disKey);
        Boolean islike = redisTemplate.opsForSet().isMember(likeKey, userId);
        Boolean isDislike = redisTemplate.opsForSet().isMember(disKey, userId);
        ProductionInfo info = new ProductionInfo();
        info.setLikes(like);
        info.setDisLikes(dislike);
        info.setLike(islike);
        info.setDislike(isDislike);
        return ServerResponse.success(info);
    }

}
