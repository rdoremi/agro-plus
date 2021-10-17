package com.agro.result;

public enum ResponseCode {
    SUCCESS(200, "SUCCESS"),
    ERROR(500, "ERROR");

    private  int code;
    private  String desc;//描述

    ResponseCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
