package com.agro.controller;

import com.agro.pojo.dto.VideoDto;
import com.agro.pojo.entity.Message;
import com.agro.pojo.entity.User;
import com.agro.pojo.vo.MessageVo;
import com.agro.result.ServerResponse;
import com.agro.service.MessageService;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/msg")
public class MessageControllor {
    private static String FANS = "fans";
    private static String FOLLOW = "follow";

    private static String MSG = "msg";

    @Autowired
    private UserService userService;

    @Autowired
    private WebSocket webSocket;
    @Autowired
    private VideoService videoService;
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private MessageService messageService;

    @GetMapping("/getmsg")
    public void getMsg(String userId) throws IOException {
        String follow = FOLLOW + ":" + userId;
        Set members = redisTemplate.opsForSet().members(follow);

        if (members.isEmpty()){

            return;
        }
        List<List<VideoDto>> videoDtoList = new ArrayList<>();
        for (Object member : members) {

            if (StringUtils.isNotBlank(member.toString())) {
                List<VideoDto> vdtoList = videoService.getListByUserId(member.toString());
                videoDtoList.add(vdtoList);
            }
        }
//        System.out.println(JSONObject.toJSONString(videoDtoList));
        webSocket.sendInfo(JSONObject.toJSONString(videoDtoList),userId);
    }
    @PostMapping("/addmsg")
    public ServerResponse addMsg(Message message){
            messageService.add(message);

        return ServerResponse.success();
    }
    @GetMapping("/getmsglist")
    public ServerResponse getMsgList(String userId){
//        redisTemplate.opsForList().leftPop()
//        messageService.getMessageByUserId(userId);
        List<Message> messagesList = messageService.getMsgList(userId);
        List<MessageVo> messageVoList = new ArrayList<>();
        for (Message message : messagesList) {
            User user = userService.selectById(message.getFromUserId());
            MessageVo messageVo = new MessageVo();
            messageVo.setHeadImg(user.getHeadImg());
            messageVo.setUserId(message.toString());

        }
        return ServerResponse.success(messagesList);
    }

}
