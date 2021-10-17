package com.agro.controller;


import com.agro.pojo.dto.SysUserDto;
import com.agro.result.ServerResponse;
import com.agro.service.SysUserService;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/manage")
@Slf4j
public class ManageUserController {

    @Autowired
    private SysUserService sysUserService;

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('sys:user:add')")
    public ServerResponse add(SysUserDto userDto) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        log.info("添加的用户："+userDto);
        boolean b = sysUserService.queryUserIsExist(userDto.getPhone());
        if (b){
            return ServerResponse.errorMsg("用户已存在");
        }
        SysUserDto rs = sysUserService.insert(userDto);
        return rs==null?ServerResponse.errorMsg("添加失败"):ServerResponse.successMsg("添加成功");
    }

    @GetMapping("/getlist")
//    @PreAuthorize("hasAuthority('sys:user:find')")
    public ServerResponse getManageList(@RequestParam(value = "page",defaultValue = "1") int page,
                                        @RequestParam(value = "limit",defaultValue = "20") int limit,
                                        String name){

        if (!StringUtils.isEmpty(name)){
            PageInfo users = sysUserService.selectUserByName(page,limit,name);
            return ServerResponse.success(users);
        }
        return ServerResponse.success(sysUserService.queryAllByLimit(page,limit));
    }
    @GetMapping("/delete/{id}")
    public ServerResponse deleteById(@PathVariable("id") Long id){
        boolean rs = sysUserService.deleteById(id);
        return rs?ServerResponse.successMsg("删除成功"):ServerResponse.errorMsg("删除失败");
    }

    @PostMapping("/update")
    public ServerResponse update(SysUserDto userDto) throws UnsupportedEncodingException, NoSuchAlgorithmException {

        boolean rs = sysUserService.update(userDto);
        if (rs){
            return ServerResponse.successMsg("修改成功");
        }
        return ServerResponse.errorMsg("修改失败");
    }
    @GetMapping("/update_status")
    public ServerResponse updateStatus(Integer status,Long id){
        boolean rs = sysUserService.updateStatus(status, id);
        return rs?ServerResponse.successMsg("修改成功"):ServerResponse.errorMsg("修改失败");
    }
}
