package com.agro.controller;


import com.agro.pojo.entity.SysPermission;
import com.agro.pojo.dto.PermissionDto;
import com.agro.result.ServerResponse;
import com.agro.service.SysPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/permission")
public class PermissionController {
    @Autowired
    private SysPermissionService permissionService;

    @PostMapping("/add")
    public ServerResponse add(SysPermission permission){
        boolean b = permissionService.queryPermissionIsExist(permission.getName());
        System.out.println("b "+b);
        if (b){
            return ServerResponse.errorMsg("权限已存在");
        }
        SysPermission rs = permissionService.insert(permission);
        return rs==null?ServerResponse.errorMsg("添加失败"):ServerResponse.successMsg("添加成功");
    }
    @GetMapping("/delete")
    public ServerResponse delete(Integer id){

        boolean b = permissionService.deleteById(id);
        if (b){
            return ServerResponse.successMsg("删除成功");
        }
        return ServerResponse.errorMsg("删除失败");
    }

    @GetMapping("/getlist")
    public ServerResponse getlist(){

        List<SysPermission> sysPermissions = permissionService.selectList();
        return ServerResponse.success(sysPermissions);
    }
    @GetMapping("/getParent")
    public ServerResponse getParent(){
        List<SysPermission> sysPermissions = permissionService.selectParent();
        return ServerResponse.success(sysPermissions);
    }
    @GetMapping("/getTreelist")
    public ServerResponse getTreeList(){
        List<PermissionDto> treeList = permissionService.getTreeList();
        return ServerResponse.success(treeList);
    }

    @PostMapping("/update")
    public ServerResponse update(SysPermission permission){
        SysPermission rs = permissionService.update(permission);
        return rs==null?ServerResponse.errorMsg("更新失败"):ServerResponse.successMsg("更新成功");
    }
    @GetMapping("/getPermissionByRoleId")
    public ServerResponse getPermissionByRoleId(String roleId){
        List<SysPermission> sysPermissions =permissionService.selectByRoleId(roleId);
        return ServerResponse.success(sysPermissions);
    }
}
