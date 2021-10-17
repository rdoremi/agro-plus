package com.agro.utils;

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.UUID;

//import org.apache.tomcat.util.http.fileupload.FileUtils;
public class FileUploadUtils {
    public static String file_path = "D:/upload/farmer";
    static {
        InputStream stream = FileUtils.class.getClassLoader().getResourceAsStream("fileupload.properties");
        Properties properties = new Properties();
        try {
            properties.load(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String filepath = properties.getProperty("filepath");
        if (filepath != null){
            file_path = filepath;
        }
    }

    public static String createNewFileName(String oldName){
        String stuff = oldName.substring(oldName.lastIndexOf("."),oldName.length());
        String newName = UUID.randomUUID()+stuff;
        return newName;
    }

    /*文件下载*/
    public static ResponseEntity<byte[]> createEntity(String path){

        File file = new File(file_path,path);

        if (file.exists()){

            byte[] bytes = null;
            try {
                bytes = FileUtils.readFileToByteArray(file);
//                bytes = cn.hutool.core.io.FileUtil.readBytes(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//            httpHeaders.setContentType(MediaType.IMAGE_JPEG);
//           httpHeaders.setContentType(MediaType.ALL);
//            httpHeaders.setContentDispositionFormData("attachment","123.jap");//设置下载的名字

            ResponseEntity<byte[]> entity= new ResponseEntity<byte[]>(bytes, httpHeaders, HttpStatus.CREATED);

            return entity;

        }else {
            System.out.println("文件不存在");
        }
        return null;
    }


}
