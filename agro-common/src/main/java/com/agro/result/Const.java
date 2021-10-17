package com.agro.result;

public class Const {



    public static final String CURRENT_USER = "sys";
    public static final String FORE_CURRENT_USER = "foreCurrent";

    public interface Role{
        int ROLE_CUSTOMER = 0;//普通用户
        int ROLE_ADMIN = 1;//管理员
        int ROLE_SUPER_ADMIN = 2;//超级管理员

    }

    public interface UserRole{
        int ROLE_STUDENT = 0;//
        int ROLE_TEACHER = 1;//
        int ROLE_ADMIN = 2;//
        int ROLE_MANAGE = 2;//
    }


    public interface Status{
        int VALID = 2;//启用
        int DISENABLE = 0;//未启用
        int LOCKED = 1;
    }

    public interface VideStatus{
        int PASS = 200;//审核通过
        int NOT_PASS = 100;//审核未通过
        int WAITCONFIRM = 300;//待审核
    }

    public interface CollectType{
       int VIDEO = 1;
       int ARTICLE = 2;
    }

    public interface Service{
        public static final String APPSERVICE = "agro-app";
        public static final String USERSERVICE = "user-service";
        public static final String ARTICLESERVICE = "article-service";
        public static final String VIDEOSERVICE = "video-service";
        public static final String COMMONSERVICE = "common-service";
    }
}
