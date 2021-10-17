package com.agro.service.impl;


import com.agro.mapper.SysRolePermissionMapper;

import com.agro.pojo.entity.SysRolePermission;
import com.agro.service.SysRolePermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service("sysRolePermissionService")
public class SysRolePermissionServiceImpl implements SysRolePermissionService {
    @Resource
    private SysRolePermissionMapper sysRolePermissionDao;


    @Override
    public SysRolePermission queryById(Integer roleId) {
        return this.sysRolePermissionDao.selectByPrimaryKey(roleId);
    }


    @Override
    public List<SysRolePermission> queryAllByLimit(int offset, int limit) {
        return null;
    }


    @Override
    public SysRolePermission insert(SysRolePermission sysRolePermission) {
        this.sysRolePermissionDao.insert(sysRolePermission);
        return sysRolePermission;
    }


    @Override
    public SysRolePermission update(SysRolePermission sysRolePermission) {
        this.sysRolePermissionDao.updateByPrimaryKey(sysRolePermission);
        return this.queryById(sysRolePermission.getRoleId());
    }


    @Override
    public boolean deleteById(Integer roleId) {
        return this.sysRolePermissionDao.deleteByPrimaryKey(roleId) > 0;
    }

    @Override
    public int save(List<SysRolePermission> sysRolePermissions) {
        int rs = sysRolePermissionDao.insertList(sysRolePermissions);
        return rs;
    }
}