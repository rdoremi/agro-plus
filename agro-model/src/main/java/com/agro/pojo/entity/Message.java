package com.agro.pojo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

public class Message {
    @Id
    private String id;
    @Column(name = "from_user_id")
    private String fromUserId;
    @Column(name = "to_user_id")
    private String toUserId;
    private String msg;
    private Integer isread;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date pastDue;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date sendtime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getIsread() {
        return isread;
    }

    public void setIsread(Integer isread) {
        this.isread = isread;
    }

    public Date getPastDue() {
        return pastDue;
    }

    public void setPastDue(Date pastDue) {
        this.pastDue = pastDue;
    }

    public Date getSendtime() {
        return sendtime;
    }

    public void setSendtime(Date sendtime) {
        this.sendtime = sendtime;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id='" + id + '\'' +
                ", fromUserId='" + fromUserId + '\'' +
                ", toUserId='" + toUserId + '\'' +
                ", msg='" + msg + '\'' +
                ", isread=" + isread +
                ", pastDue=" + pastDue +
                ", sendtime=" + sendtime +
                '}';
    }
}
