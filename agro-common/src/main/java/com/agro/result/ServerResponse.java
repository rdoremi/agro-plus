package com.agro.result;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;


public class ServerResponse<T>  implements Serializable{
    private static final long serialVersionUID = 6220674416625330860L;

    private int status;
    private String msg;
    private T data;

    public ServerResponse(){
        this.status = 501;
        this.data = null;
        this.msg =null;
    }

    private ServerResponse(int status) {
        this.status = status;
    }

    private ServerResponse(int status, T data) {
        this.status = status;
        this.data = data;
    }

    private ServerResponse(int status, String msg, T data) {
        this.status = status;
        this.data = data;
        this.msg = msg;
    }

    private ServerResponse(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    @JsonIgnore//此方法将不在序列化结果里
    public boolean isSuccess() {
        return this.status == ResponseCode.SUCCESS.getCode();
    }

    public int getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }

    public String getMsg() {
        return msg;
    }

    public static <T> ServerResponse<T> success() {
        return new ServerResponse<>(ResponseCode.SUCCESS.getCode());//ServerResponse(int status)
    }

    public static <T> ServerResponse<T> success(T data) {
        return new ServerResponse<>(ResponseCode.SUCCESS.getCode(),ResponseCode.SUCCESS.getDesc(), data);//private ServerResponse(int status,T data){
    }

    public static <T> ServerResponse<T> successMsg(String msg) {
        return new ServerResponse<>(ResponseCode.SUCCESS.getCode(), msg);// private ServerResponse(int status,String msg){
    }

    public static <T> ServerResponse<T> error() {
        return new ServerResponse<T>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getDesc());
    }

    public static <T> ServerResponse<T> errorMsg(String errorMessage) {
        return new ServerResponse<>(ResponseCode.ERROR.getCode(), errorMessage);
    }

}
