package com.agro.service;



import com.agro.pojo.dto.PermissionDto;
import com.agro.pojo.entity.SysPermission;

import java.util.List;


public interface SysPermissionService {


    SysPermission queryById(Integer id);


    List<SysPermission> queryAllByLimit(int offset, int limit);

    List<SysPermission> selectList();


    SysPermission insert(SysPermission sysPermission);


    SysPermission update(SysPermission sysPermission);




    boolean deleteById(Integer id);

    List<SysPermission> selectParent();

    List<PermissionDto> getTreeList();

    SysPermission getById(Long id);

    List<SysPermission> getByUserId(Long id);

    boolean queryPermissionIsExist(String name);

    List<SysPermission> selectByRoleId(String roleId);
}