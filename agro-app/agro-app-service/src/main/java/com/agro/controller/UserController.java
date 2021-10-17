package com.agro.controller;

import com.agro.pojo.dto.UserDto;
import com.agro.pojo.entity.User;
import com.agro.result.ServerResponse;
import com.agro.utils.UuidUtil;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/fore/user")
@Slf4j
//@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate redisTemplate;



    @PostMapping("/register")
    public ServerResponse add(User user) throws UnsupportedEncodingException, NoSuchAlgorithmException {

        if(StringUtils.isBlank(user.getTel())||StringUtils.isBlank(user.getPassword())){
            return ServerResponse.errorMsg("手机号或密码不能为空");
        }

        boolean b = userService.queryUserIsExist(user.getTel());
        if (!b){
            return ServerResponse.errorMsg("用户已存在");
        }

        User insert = userService.insert(user);
        return insert==null?ServerResponse.errorMsg("注册失败"):ServerResponse.successMsg("注册成功");
    }
    @PostMapping("/login")
    public ServerResponse login(UserDto userDto, HttpSession session) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        if(StringUtils.isBlank(userDto.getTel())||StringUtils.isBlank(userDto.getPassword())){
            return ServerResponse.errorMsg("手机号或密码不能为空");
        }
        log.info("token->"+userDto);
        //String tokenCode = (String) session.getAttribute(userDto.getImgToken());
        String tokenCode = (String) redisTemplate.opsForValue().get(userDto.getImgToken());
        if (StringUtils.isEmpty(tokenCode)){
            return ServerResponse.errorMsg("验证码已失效");
        }
        if (!tokenCode.equals(userDto.getImgCode())){
            return ServerResponse.errorMsg("验证码错误");
        }
        redisTemplate.delete(userDto.getImgToken());
        //session.removeAttribute(userDto.getImgToken());
        String token = UuidUtil.getShortUuid();
        userDto.setToken(token);
        redisTemplate.opsForValue().set(token,JSON.toJSONString(userDto),3600, TimeUnit.SECONDS);


        User login = userService.login(userDto);
        return login==null?ServerResponse.errorMsg("用户名或密码错误"):ServerResponse.success(login);
    }

    @GetMapping("/logout/{token}")
    public ServerResponse logout(@PathVariable("token") String token){

        redisTemplate.delete(token);
        return ServerResponse.success(new UserDto());
    }

    @PostMapping("/wxregister")
    public ServerResponse wxRegister(User user) throws UnsupportedEncodingException, NoSuchAlgorithmException {

        User us = userService.selectById(user.getId());
        if (us == null){

            User rs = userService.wxRegester(user);
            return rs==null?ServerResponse.errorMsg("注册失败"):ServerResponse.success(rs);
        }
        return ServerResponse.success(us);
    }

    @GetMapping("/getlist")
    public ServerResponse getList(@RequestParam(value = "page",defaultValue = "1") int page,@RequestParam(value = "limit",defaultValue = "20") int limit,String name){

        if (!StringUtils.isEmpty(name)){
            PageInfo users = userService.selectUserByName(page,limit,name);
            return ServerResponse.success(users);
        }
        PageInfo<User> userPageInfo = userService.queryAllByLimit(page, limit);

        return ServerResponse.success(userPageInfo);
    }

    @GetMapping("/delete")
    public ServerResponse delete(String id){
        boolean b = userService.deleteById(id);
        return b?ServerResponse.successMsg("删除成功"):ServerResponse.errorMsg("删除失败");
    }

    @GetMapping("/update_status")
    public ServerResponse updateStatus(Integer status,String id){
        boolean rs = userService.updateStatus(status, id);
        return rs?ServerResponse.successMsg("修改成功"):ServerResponse.errorMsg("修改失败");
    }

    @PostMapping("/update")
    public ServerResponse update(User user){

        boolean update = userService.update(user);

        return update?ServerResponse.successMsg("修改成功"):ServerResponse.errorMsg("修改失败");
    }

    @GetMapping("/getstatus")
    public ServerResponse getstatus(String userId){

        User us = userService.selectById(userId);
        return ServerResponse.success(us);
    }
    @GetMapping("/getinfo")
    public ServerResponse getinfo(String userId){
        User user = userService.selectById(userId);
        return ServerResponse.success(user);
    }
}
