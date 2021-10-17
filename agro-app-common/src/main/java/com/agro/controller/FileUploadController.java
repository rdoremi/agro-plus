package com.agro.controller;


import com.agro.result.ServerResponse;
import com.agro.utils.DateTimeUtil;
import com.agro.utils.FileUploadUtils;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.ObjectMetadata;
import org.joda.time.DateTime;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@RequestMapping("/file")
@RestController
@CrossOrigin
public class FileUploadController {
    private static String ENDPOINT = "oss-cn-chengdu.aliyuncs.com";
    // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录RAM控制台创建RAM账号。
    private static String ACCESSKEYID = "";
    private static String ACCESSKEYSECRET = "";
    private static String BUCKETNAME = "";

    @PostMapping(value = "/upload")
    public ServerResponse fileUpload(MultipartFile upfile) throws IOException {

        String oldName = upfile.getOriginalFilename();
        String newName = FileUploadUtils.createNewFileName(oldName);
        String fileName = DateTimeUtil.dateToStr(new Date(),"yyyyMMdd");
        System.out.println("lodname->"+oldName);
        File file = new File(FileUploadUtils.file_path,fileName);
        if (!file.exists()){
            file.mkdirs();
        }
        File newFile = new File(file,newName);
        upfile.transferTo(newFile);
        Map<String,Object> map = new HashMap<>();
        map.put("path",fileName+"/"+newName);
        System.out.println(map);
        return ServerResponse.success(map);
    }
    @GetMapping("/file_download")
    public ResponseEntity<byte[]> fileDownLoad(String path)  {
        return FileUploadUtils.createEntity(path);
    }


    @PostMapping("/oss_upload")
    public ServerResponse uploadFileAvatar(MultipartFile file) {
        String datePath = new DateTime().toString("yyyy/MM/dd");
        String ossFileName = "video"+"/"+datePath;
        ServerResponse upload = upload(file, ossFileName);
        return upload;


    }

    @PostMapping("/oss_upload_pdf")
    public ServerResponse uploadPdf(MultipartFile file) {
        String datePath = new DateTime().toString("yyyy/MM/dd");
        String ossFileName = "pdf"+"/"+datePath;
        ServerResponse upload = upload(file, ossFileName);
        return upload;


    }


    @PostMapping("/oss_upload_image")
    public ServerResponse uploadImage(MultipartFile img_file){
        String datePath = new DateTime().toString("yyyy/MM/dd");
        String ossFileName = "images"+"/"+datePath;
        ServerResponse upload = upload(img_file, ossFileName);
        return upload;
    }

    @PostMapping("/oss_upload_cover_image")
    public ServerResponse uploadCoverImage(MultipartFile file){
        String datePath = new DateTime().toString("yyyy/MM/dd");
        String ossFileName = "images"+"/"+datePath;
        ServerResponse upload = upload(file, ossFileName);
        return upload;
    }
    @PostMapping("/oss_upload_head_image")
    public ServerResponse uploadHeadImage(MultipartFile file,String userId){
//        String datePath = new DateTime().toString("yyyy/MM/dd");
        String ossFileName = "headImages"+"/"+userId;
        ServerResponse upload = upload(file, ossFileName);
        return upload;
    }

    private ServerResponse upload(MultipartFile file,String ossFileName){
        // 创建OSSClient实例。
        try {
            // 创建OSS实例。
            OSS ossClient = new OSSClientBuilder().build(ENDPOINT, ACCESSKEYID, ACCESSKEYSECRET);

            //获取上传文件输入流
            InputStream inputStream = file.getInputStream();
            //获取文件名称
            String fileName = file.getOriginalFilename();
            ObjectMetadata objectMetadata = new ObjectMetadata();
            //objectMetadata.setContentType(getcontentType(fileName.substring(fileName.lastIndexOf("."))));
            objectMetadata.setContentType(String.valueOf(MediaType.APPLICATION_OCTET_STREAM));

            //1 在文件名称里面添加随机唯一的值
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            // yuy76t5rew01.jpg
            fileName = uuid + fileName;

            //2 把文件按照日期进行分类
            //获取当前日期
            //   2019/11/12
            String datePath = new DateTime().toString("yyyy/MM/dd");
            //拼接
            //  2019/11/12/ewtqr313401.jpg
//            fileName = datePath + "/" + fileName;
            fileName = ossFileName+"/"+fileName;

            //调用oss方法实现上传
            //第一个参数  Bucket名称
            //第二个参数  上传到oss文件路径和文件名称   aa/bb/1.jpg
            ossClient.putObject(BUCKETNAME, fileName, inputStream);
            //第三个参数  上传文件输入流

            // 关闭OSSClient。
            ossClient.shutdown();

            //把上传之后文件路径返回
            //需要把上传到阿里云oss路径手动拼接出来
            //  https://edu-guli-1010.oss-cn-beijing.aliyuncs.com/01.jpg
            String url = "https://" + BUCKETNAME + "." + ENDPOINT + "/" + fileName;
            System.out.println("url " + url);
            return ServerResponse.success(url);
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    public static String getcontentType(String FilenameExtension) {
        if (FilenameExtension.equalsIgnoreCase(".bmp")) {
            return "image/bmp";
        }
        if (FilenameExtension.equalsIgnoreCase(".gif")) {
            return "image/gif";
        }
        if (FilenameExtension.equalsIgnoreCase(".jpeg") ||
                FilenameExtension.equalsIgnoreCase(".jpg") ||
                FilenameExtension.equalsIgnoreCase(".png")) {
            return "image/jpg";
        }
        if (FilenameExtension.equalsIgnoreCase(".html")) {
            return "text/html";
        }
        if (FilenameExtension.equalsIgnoreCase(".txt")) {
            return "text/plain";
        }
        if (FilenameExtension.equalsIgnoreCase(".vsd")) {
            return "application/vnd.visio";
        }
        if (FilenameExtension.equalsIgnoreCase(".pptx") ||
                FilenameExtension.equalsIgnoreCase(".ppt")) {
            return "application/vnd.ms-powerpoint";
        }
        if (FilenameExtension.equalsIgnoreCase(".docx") ||
                FilenameExtension.equalsIgnoreCase(".doc")) {
            return "application/msword";
        }
        if (FilenameExtension.equalsIgnoreCase(".xml")) {
            return "text/xml";
        }
        if (FilenameExtension.equalsIgnoreCase(".mp4")||
                FilenameExtension.equalsIgnoreCase(".flv")) {
            return "video/mp4";
        }
        return "image/jpg";
    }
}
