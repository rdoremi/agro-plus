package com.agro.service.impl;


import com.agro.dto.SysUserLogin;
import com.agro.pojo.entity.SysPermission;
import com.agro.pojo.entity.SysUser;
import com.agro.result.Const;
import com.agro.service.SysPermissionService;
import com.agro.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private SysPermissionService permissionDao;



    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        SysUser sysUser = sysUserService.getUser(s);
        log.info("登录："+sysUser);
        if (sysUser == null){
            throw new AuthenticationCredentialsNotFoundException("用户不存在");
        }else if (sysUser.getStatus() == Const.Status.LOCKED){
            throw new LockedException("用户被锁定");
        }
        SysUserLogin userLogin = new SysUserLogin();
        BeanUtils.copyProperties(sysUser,userLogin);

        List<SysPermission> permissionList = permissionDao.getByUserId(sysUser.getId());
        /*List<SysPermission> sysPermissionList = new ArrayList<>();

        for (SysRolePermission sysRolePermission : rolePermissionList) {
            List<SysPermission> permissionList = permissionDao.selectByParentId(sysRolePermission.getPermissionId());
            for (SysPermission permission : permissionList) {
                sysPermissionList.add(permission);
            }
        }*/
        log.info("permissionList-> "+permissionList);
        for (SysPermission sysPermission : permissionList) {
            log.info(sysPermission.getPermission());
        }
        userLogin.setPermissions(permissionList);
        return userLogin;
    }
}
