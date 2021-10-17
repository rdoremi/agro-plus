package com.agro.controller;

import com.agro.config.CreateImageCode;
import com.agro.result.ServerResponse;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/kaptcha")
//@CrossOrigin
public class KaptchaController {

    @Autowired
    @Qualifier("getDefaultKaptcha")
     private DefaultKaptcha defaultKaptcha;

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/img_code/{imgToken}")
    public void imgCode(@PathVariable("imgToken") String imgToken, HttpServletRequest request, HttpServletResponse response) throws IOException {
        ByteArrayOutputStream jpegStream = new ByteArrayOutputStream();
        try {
        String createText = defaultKaptcha.createText();
        request.getSession().setAttribute(imgToken,createText);

        BufferedImage challenge = defaultKaptcha.createImage(createText);

            ImageIO.write(challenge,"jpg",jpegStream);
        } catch (IllegalArgumentException e) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // 定义response输出类型为image/jpeg类型，使用response输出流输出图片的byte数组
        byte[] captchaChallengeAsJpeg = jpegStream.toByteArray();
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        ServletOutputStream responseOutputStream = response.getOutputStream();
        responseOutputStream.write(captchaChallengeAsJpeg);
        responseOutputStream.flush();
        responseOutputStream.close();
    }
    @GetMapping("/code/{token}")
    public void getCode3(@PathVariable("token") String token,HttpServletRequest req, HttpServletResponse response,HttpSession session) throws IOException{


        // 设置响应的类型格式为图片格式
        response.setContentType("image/jpeg");
        //禁止图像缓存。
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        CreateImageCode vCode = new CreateImageCode(100,30,5,10);

        redisTemplate.opsForValue().set(token,vCode.getCode(),300,TimeUnit.SECONDS);

        //session.setAttribute(token, vCode.getCode());
        vCode.write(response.getOutputStream());

    }

    @GetMapping("/check")
    public ServerResponse check(String token,String imgCode){
        String tokenCode = (String) redisTemplate.opsForValue().get(token);

        if (StringUtils.isEmpty(tokenCode)){
            return ServerResponse.errorMsg("验证码已失效");
        }
        if (!tokenCode.equals(imgCode)){
            return ServerResponse.errorMsg("验证码错误");
        }
        redisTemplate.delete(imgCode);

        return ServerResponse.success();
    }
}
