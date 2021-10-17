package com.agro.service;

import com.agro.pojo.entity.Message;

import java.util.List;

public interface MessageService {

    public List<Message> messageList(String fromUserId,String toUserId);
    public int add(Message message);

    List<Message> getMsgList(String userId);
}
