package com.agro.service;


import com.agro.pojo.dto.SysUserDto;
import com.agro.pojo.entity.SysUser;
import com.github.pagehelper.PageInfo;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * (SysUser)表服务接口
 *
 * @author makejava
 * @since 2020-07-17 15:00:07
 */
public interface SysUserService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SysUser queryById(Long id);


    PageInfo queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param userDto 实例对象
     * @return 实例对象
     */
    SysUserDto insert(SysUserDto userDto) throws UnsupportedEncodingException, NoSuchAlgorithmException;

    /**
     * 修改数据
     *
     * @param userDto 实例对象
     * @return 实例对象
     */
    Boolean update(SysUserDto userDto) throws UnsupportedEncodingException, NoSuchAlgorithmException;

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

    SysUser getUser(String tel);

    boolean queryUserIsExist(String phone);

    boolean updateStatus(Integer status, Long id);

    PageInfo selectUserByName(int page, int limit, String name);

}