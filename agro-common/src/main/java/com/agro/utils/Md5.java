package com.agro.utils;

import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5 {
    public static String EncoderByMd5(String str) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");
        BASE64Encoder base64Encoder = new BASE64Encoder();
        String newString = base64Encoder.encode(md5.digest(str.getBytes("utf-8")));
        return newString;
    }

    public static boolean checkPassword(String newPassword,String oldPassword) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        if (EncoderByMd5(newPassword).equals(oldPassword)){
            return true;
        }else {
            return false;
        }
    }
}
