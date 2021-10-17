package com.agro.pojo.vo;

/*
* 订阅的信息
* */
public class SubInfo {
    private boolean isSub;
    private Long subNum;
    private String authorId;

    public boolean isSub() {
        return isSub;
    }

    public void setSub(boolean sub) {
        isSub = sub;
    }

    public Long getSubNum() {
        return subNum;
    }

    public void setSubNum(Long subNum) {
        this.subNum = subNum;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }
}
