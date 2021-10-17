package com.agro.controller;

import com.agro.pojo.entity.User;
import com.agro.pojo.vo.FollowVo;
import com.agro.pojo.vo.SubInfo;
import com.agro.result.ServerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/subscribe")
public class SubscribeController {


    private static String FANS = "fans";
    private static String FOLLOW = "follow";
    @Autowired
    private WebSocket webSocket;
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private UserService userService;

    @PostMapping("/addOrCancel1")
    public ServerResponse subscribe(String authorId, String userId) {

        String key = FOLLOW + ":" + authorId;

        if (authorId.isEmpty() || userId.isEmpty()) {
            return ServerResponse.errorMsg("未登录");
        }

        User user = userService.selectById(authorId);

        if (user == null) {
            return ServerResponse.errorMsg("用户不存在");
        }
        Boolean isMember = redisTemplate.opsForSet().isMember(key, userId);

        if (isMember) {
            redisTemplate.opsForSet().remove(key, userId);
            return ServerResponse.successMsg("已取消");
        }

        redisTemplate.opsForSet().add(key, userId);

        return ServerResponse.successMsg("关注成功");
    }

    @PostMapping("/addOrCancel")
    public ServerResponse attention(String authorId, String userId) {
        String follow = FOLLOW + ":" + userId;
        String fans = FANS + ":" + authorId;

        if (authorId.isEmpty() || userId.isEmpty()) {
            return ServerResponse.errorMsg("未登录");
        }

        User user = userService.selectById(authorId);

        if (user == null) {
            return ServerResponse.errorMsg("用户不存在");
        }
        Boolean isFollow = redisTemplate.opsForSet().isMember(follow, authorId);
        Boolean isFans = redisTemplate.opsForSet().isMember(fans, userId);
        if (isFollow && isFans) {
            redisTemplate.opsForSet().remove(fans, userId);
            redisTemplate.opsForSet().remove(follow, authorId);
            return ServerResponse.successMsg("已取消");
        }
        redisTemplate.opsForSet().add(follow, authorId);
        redisTemplate.opsForSet().add(fans, userId);

        return ServerResponse.successMsg("关注成功");
    }

    @GetMapping("/getSubInfo")
    public ServerResponse getSubNum(String authorId, String userId) {
        String key = FANS + ":" + authorId;
        Boolean isMember = redisTemplate.opsForSet().isMember(key, userId);
        Long size = redisTemplate.opsForSet().size(key);
        SubInfo subInfo = new SubInfo();
        subInfo.setAuthorId(authorId);
        subInfo.setSub(isMember);
        subInfo.setSubNum(size);
        return ServerResponse.success(subInfo);
    }

    @GetMapping("/getfans")
    public ServerResponse getFans(String userId) {
        String key = FANS + ":" + userId;
        Set members = redisTemplate.opsForSet().members(key);
        List<FollowVo> userList = getList(members,userId);

        return ServerResponse.success(userList);
    }
    @GetMapping("/getfollow")
    public ServerResponse getFollow(String userId){
        String key = FOLLOW + ":" + userId;
        Set members = redisTemplate.opsForSet().members(key);
        List<FollowVo> userList = getList(members,userId);

        return ServerResponse.success(userList);
    }
    private List<FollowVo> getList(Set members,String userId){
        List<FollowVo> userList = new ArrayList<>();
        for (Object member : members) {
            User user = userService.selectById(member.toString());
            String follow = FOLLOW + ":" + userId;
            String fans = FANS + ":" + userId;
            Boolean isFollow = redisTemplate.opsForSet().isMember(follow, user.getId());
            Boolean isFans = redisTemplate.opsForSet().isMember(fans, user.getId());
            FollowVo followVo = new FollowVo();
            followVo.setHeadImg(user.getHeadImg());
            followVo.setUsername(user.getUsername());
            followVo.setIntro(user.getIntro());
            followVo.setId(user.getId());
            followVo.setSex(user.getSex());

            followVo.setFans(isFans);
            followVo.setFollow(isFollow);

            userList.add(followVo);
        }
        return userList;
    }
    /*@GetMapping("/getsubscribe")
    public ServerResponse getFanss(String userId){
        String key = SUB+":"+userId;
        redisTemplate.opsForZSet();
        List<User> userList = new ArrayList<>();

        for (Object member : members) {

            User user = userService.selectById(member.toString());
            user.setPassword("");
            user.setTel("");
            userList.add(user);
        }

        println(userList);
        return ServerResponse.success(userList);
    }*/




}
