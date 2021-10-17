package com.agro.pojo.entity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

public class Collect {
    @Id
    private String id;

    @Column(name = "production_id")
    private String productionId;

    @Column(name = "user_id")
    private String userId;

    private Integer type;

    @Column(name = "create_time")
    private Date createTime;

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
     * @return production_id
     */
    public String getProductionId() {
        return productionId;
    }

    /**
     * @param productionId
     */
    public void setProductionId(String productionId) {
        this.productionId = productionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return type
     */
    public Integer getType() {
        return type;
    }

    /**
     * @param type
     */
    public void setType(Integer type) {
        this.type = type;
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

    @Override
    public String toString() {
        return "Collect{" +
                "id='" + id + '\'' +
                ", productionId='" + productionId + '\'' +
                ", userId='" + userId + '\'' +
                ", type=" + type +
                ", createTime=" + createTime +
                '}';
    }
}