package com.agro.service.impl;


import com.agro.mapper.SysRoleUserMapper;

import com.agro.pojo.entity.SysRoleUser;
import com.agro.service.SysRoleUserService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;


@Service("sysRoleUserService")
public class SysRoleUserServiceImpl implements SysRoleUserService {
    @Resource
    private SysRoleUserMapper sysRoleUserDao;


    @Override
    public SysRoleUser queryById(Long userId) {
        return this.sysRoleUserDao.selectByPrimaryKey(userId);
    }


    @Override
    public List<SysRoleUser> queryAllByLimit(int offset, int limit) {
        return null;
    }


    @Override
    public SysRoleUser insert(SysRoleUser sysRoleUser) {
        this.sysRoleUserDao.insert(sysRoleUser);
        return sysRoleUser;
    }


    @Override
    public boolean update(SysRoleUser sysRoleUser) {
        this.sysRoleUserDao.updateByPrimaryKey(sysRoleUser);
        Example example = new Example(SysRoleUser.class);
        example.createCriteria().andEqualTo("userId",sysRoleUser.getUserId());
        int i = sysRoleUserDao.updateByExampleSelective(sysRoleUser, example);
        if (i>0){
            return true;
        }
        return false;
    }


    @Override
    public boolean deleteById(Long userId) {
        return this.sysRoleUserDao.deleteByPrimaryKey(userId) > 0;
    }

    @Override
    public int save(SysRoleUser sysRoleUser) {
        return sysRoleUserDao.insert(sysRoleUser);
    }
}