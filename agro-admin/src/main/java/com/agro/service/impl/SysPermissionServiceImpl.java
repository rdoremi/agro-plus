package com.agro.service.impl;


import com.agro.mapper.SysPermissionMapper;
import com.agro.mapper.SysRolePermissionMapper;

import com.agro.pojo.dto.PermissionDto;
import com.agro.pojo.entity.SysPermission;
import com.agro.pojo.entity.SysRolePermission;
import com.agro.service.SysPermissionService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


@Service("sysPermissionService")
public class SysPermissionServiceImpl implements SysPermissionService {
    @Resource
    private SysPermissionMapper sysPermissionDao;

    @Resource
    private SysRolePermissionMapper sysRolePermissionMapper;


    @Override
    public SysPermission queryById(Integer id) {
        return this.sysPermissionDao.selectByPrimaryKey(id);
    }


    @Override
    public List<SysPermission> queryAllByLimit(int offset, int limit) {
        return null;
    }

    @Override
    public List<SysPermission> selectList() {
        return sysPermissionDao.selectAll();
    }

    @Override
    public SysPermission insert(SysPermission sysPermission) {

        System.out.println("permission: " + sysPermission.getParentId());
        if (sysPermission.getParentId() == null) {
            sysPermission.setParentId(0);
        }
        int rs = this.sysPermissionDao.insert(sysPermission);

        return rs > 0 ? sysPermission : null;
    }

    @Override
    public SysPermission update(SysPermission sysPermission) {
        int rs = this.sysPermissionDao.updateByPrimaryKey(sysPermission);
        return rs > 0 ? sysPermission : null;
    }


    @Override
    public boolean deleteById(Integer id) {
        return this.sysPermissionDao.deleteByPrimaryKey(id) > 0;
    }

    @Override
    public List<SysPermission> selectParent() {
        Example example = new Example(SysPermission.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("parentId", 0);

        List<SysPermission> sysPermissions = sysPermissionDao.selectByExample(example);

        return sysPermissions;
    }

    @Override
    public List<PermissionDto> getTreeList() {
        List<SysPermission> permissionList = sysPermissionDao.selectAll();
        List<SysPermission> pList = new ArrayList<>();
        List<PermissionDto> pDtoList = new ArrayList<>();
        for (SysPermission permission : permissionList) {
            if (permission.getParentId() == 0) {
                PermissionDto permissionDto = new PermissionDto();
                permissionDto.setId(permission.getId());
                permissionDto.setName(permission.getName());
                pDtoList.add(permissionDto);
            }
        }
        for (int i = 0; i < pDtoList.size(); i++) {
            List<SysPermission> list = new ArrayList<>();
            for (SysPermission permission : permissionList) {

                if (permission.getParentId() == pDtoList.get(i).getId()) {

                    list.add(permission);

                }
                pDtoList.get(i).setCList(list);
            }
        }

        return pDtoList;
    }

    @Override
    public SysPermission getById(Long id) {

        return sysPermissionDao.selectByPrimaryKey(id);
    }

    @Override
    public List<SysPermission> getByUserId(Long id) {
        List<SysPermission> sysPermissions = sysPermissionDao.selectListByUserId(id);
        return sysPermissions;
    }

    @Override
    public boolean queryPermissionIsExist(String name) {
        Example example = new Example(SysPermission.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("name", name);
        SysPermission sysPermission = sysPermissionDao.selectOneByExample(example);
        System.out.println(sysPermission);
        return sysPermission == null ? false : true;
    }

    @Override
    public List<SysPermission> selectByRoleId(String roleId) {


        Example example = new Example(SysRolePermission.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("roleId", roleId);
        List<SysRolePermission> sysPermissions = sysRolePermissionMapper.selectByExample(example);

        List<SysPermission> permissionList = new ArrayList<>();
        for (SysRolePermission sysPermission : sysPermissions) {
            SysPermission sysp = sysPermissionDao.selectByPrimaryKey(sysPermission.getPermissionId());
            permissionList.add(sysp);
        }

        return permissionList;
    }
}