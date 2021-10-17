package com.agro.controller;



import com.agro.pojo.dto.RoleDto;
import com.agro.pojo.entity.SysRole;
import com.agro.result.ServerResponse;
import com.agro.service.SysRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/role")
@Slf4j
public class RoleController {
    @Autowired
    private SysRoleService roleService;


    @PostMapping("/add")
    public ServerResponse add(RoleDto roleDto){
//        return roleService.insert(sysRole);
        log.info(roleDto.toString());
        RoleDto rs = roleService.save(roleDto);
        return rs==null?ServerResponse.errorMsg("添加失败"):ServerResponse.successMsg("添加成功");
    }

    @GetMapping("/getlist")
    public ServerResponse getlist(){

        List<SysRole> sysRoles = roleService.queryAll();
        return ServerResponse.success(sysRoles);
    }

    @PostMapping("/update")
    public ServerResponse update(SysRole sysRole){
        boolean rs = roleService.update(sysRole);

        return rs?ServerResponse.successMsg("修改成功"):ServerResponse.errorMsg("修改失败");
    }

    @GetMapping("/delete")
    public ServerResponse delete(Integer id){

        boolean b = roleService.deleteById(id);
        return b?ServerResponse.successMsg("删除成功"):ServerResponse.errorMsg("删除失败");
    }



}
