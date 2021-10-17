package com.agro.pojo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * (User)实体类
 *
 * @author makejava
 * @since 2020-07-25 14:13:25
 */
public class UserDto implements Serializable {
    private static final long serialVersionUID = -77284092380451710L;
    
    private Long id;
    
    private String username;
    
    private String password;
    
    private String tel;
    
    private Integer sex;
    
    private String idcart;
    
    private String headImg;
    
    private String email;

    private String imgCode;

    private String imgToken;

    private Integer subScribeNumber;

    //登录凭证
    private String token;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date updateTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getIdcart() {
        return idcart;
    }

    public void setIdcart(String idcart) {
        this.idcart = idcart;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getImgCode() {
        return imgCode;
    }

    public void setImgCode(String imgCode) {
        this.imgCode = imgCode;
    }

    public String getImgToken() {
        return imgToken;
    }

    public void setImgToken(String imgToken) {
        this.imgToken = imgToken;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getSubScribeNumber() {
        return subScribeNumber;
    }

    public void setSubScribeNumber(Integer subScribeNumber) {
        this.subScribeNumber = subScribeNumber;
    }



    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", tel='" + tel + '\'' +
                ", sex='" + sex + '\'' +
                ", idcart='" + idcart + '\'' +
                ", headImg='" + headImg + '\'' +
                ", email='" + email + '\'' +
                ", imgCode='" + imgCode + '\'' +
                ", imgToken='" + imgToken + '\'' +
                ", token='" + token + '\'' +
                ", subScribeNumber='" + subScribeNumber + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}