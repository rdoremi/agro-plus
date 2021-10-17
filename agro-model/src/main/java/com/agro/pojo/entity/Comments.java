package com.agro.pojo.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

public class Comments {
    @Id
    private String id;

    @Column(name = "production_id")
    private String productionId;

    private Integer likes;

    private Integer dislike;

    @Column(name = "reply_id")
    private String replyId;

    @Column(name = "head_img")
    private String headImg;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    private String content;

    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return productionId
     */
    public String getProductionId() {
        return productionId;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @param productionId
     */
    public void setProductionId(String productionId) {
        this.productionId = productionId;
    }

    public String getReplyId() {
        return replyId;
    }

    public void setReplyId(String replyId) {
        this.replyId = replyId;
    }

    /**
     * @return like
     */
    public Integer getLikes() {
        return likes;
    }

    /**
     * @param likes
     */
    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    /**
     * @return dislike
     */
    public Integer getDislike() {
        return dislike;
    }

    /**
     * @param dislike
     */
    public void setDislike(Integer dislike) {
        this.dislike = dislike;
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * @return content
     */
    public String getContent() {
        return content;
    }

    /**
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Comments{" +
                "id='" + id + '\'' +
                ", productionId='" + productionId + '\'' +
                ", like=" + likes +
                ", dislike=" + dislike +
                ", replyId='" + replyId + '\'' +
                ", headImg='" + headImg + '\'' +
                ", userName='" + userName + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", content='" + content + '\'' +
                '}';
    }
}