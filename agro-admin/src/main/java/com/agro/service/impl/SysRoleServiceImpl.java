package com.agro.service.impl;


import com.agro.mapper.SysRoleMapper;
import com.agro.mapper.SysRolePermissionMapper;

import com.agro.pojo.dto.RoleDto;
import com.agro.pojo.entity.SysRole;
import com.agro.pojo.entity.SysRolePermission;
import com.agro.service.SysRolePermissionService;
import com.agro.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service("sysRoleService")
@Transactional
public class SysRoleServiceImpl implements SysRoleService {
    @Resource
    private SysRoleMapper sysRoleDao;

    @Autowired
    private SysRolePermissionService rolePermissionDao;

    @Autowired
    private SysRolePermissionMapper sysRolePermissionMapper;


    @Override
    public SysRole queryById(Integer id) {
        return this.sysRoleDao.selectByPrimaryKey(id);
    }

    @Override
    public List<SysRole> queryAllByLimit(int offset, int limit) {
        return null;
    }


    @Override
    public SysRole insert(SysRole sysRole) {
        this.sysRoleDao.insert(sysRole);
        return sysRole;
    }

    @Override
    public RoleDto save(RoleDto roleDto) {

        SysRole sysRole = new SysRole();
        sysRole.setName(roleDto.getName());
        sysRole.setDescription(roleDto.getDescription());
        sysRole.setCreateTime(new Date());
        sysRole.setUpdateTime(new Date());
        int rsRole = sysRoleDao.insert(sysRole);
        if (rsRole > 0){
            Example example = new Example(SysRole.class);
            example.createCriteria().andEqualTo("name",roleDto.getName());
//            SysRole newCheck = sysRoleDao.selectByPrimaryKey(roleDto.getName());
            SysRole newCheck = sysRoleDao.selectOneByExample(example);

            List<SysRolePermission> sysRolePermissions = new ArrayList<>();
            String[] idlist = roleDto.getIdList().split(",");
            for (int i = 0; i < idlist.length; i++) {
                SysRolePermission rolePermission = new SysRolePermission();
                rolePermission.setPermissionId(Integer.valueOf(idlist[i]));
                rolePermission.setRoleId(newCheck.getId());
                sysRolePermissions.add(rolePermission);
            }
            int rs = rolePermissionDao.save(sysRolePermissions);
            return rs>0?roleDto:null;
        }
        return null;
    }

    @Override
    public boolean update(SysRole sysRole) {
        sysRole.setUpdateTime(new Date());
        int i = this.sysRoleDao.updateByPrimaryKey(sysRole);
        if (i > 0){
            return true;
        }
        return false;
//        return this.queryById(sysRole.getId());
    }


    @Override
    public boolean deleteById(Integer id) {

        int rs = sysRoleDao.deleteByPrimaryKey(id);

        return rs > 0;
    }

    @Override
    public List<SysRole> queryAll() {

        return sysRoleDao.selectAll();
    }
}