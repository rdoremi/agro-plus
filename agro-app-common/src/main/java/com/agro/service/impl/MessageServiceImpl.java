package com.agro.service.impl;

import com.agro.mapper.MessageMapper;
import com.agro.pojo.entity.Message;
import com.agro.service.MessageService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

@Service("messageServiceImpl")
public class MessageServiceImpl implements MessageService {
    @Resource
    private MessageMapper messageMapper;

    @Override
    public List<Message> messageList(String fromUserId, String toUserId) {
        Example example = new Example(Message.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("fromUserId", fromUserId)
                .andEqualTo("toUserId", toUserId);
        List<Message> messages = messageMapper.selectByExample(example);
        return messages;
    }

    @Override
    public int add(Message message) {
        if (message == null) {
            System.out.println("message为空");
            return 0;
        }
        System.out.println(message);
        return messageMapper.insertSelective(message);
    }

    @Override
    public List<Message> getMsgList(String userId) {
        Example example = new Example(Message.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("fromUserId", userId)
                .orEqualTo("toUserId", userId);
        example.orderBy("sendtime").desc();
        List<Message> messages = messageMapper.selectByExample(example);
        return messages;
    }
}
