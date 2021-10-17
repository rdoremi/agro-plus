package com.agro.service;


import com.agro.pojo.dto.UserDto;
import com.agro.pojo.entity.User;
import com.github.pagehelper.PageInfo;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * (User)表服务接口
 *
 * @author makejava
 * @since 2020-07-25 14:13:25
 */
public interface UserService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    User queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    PageInfo<User> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    User insert(User user) throws UnsupportedEncodingException, NoSuchAlgorithmException;

    boolean queryUserIsExist(String tel);

    /**
     * 修改数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    boolean update(User user);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(String id);

    @Transactional(propagation = Propagation.REQUIRED)
    User wxRegester(User user) throws UnsupportedEncodingException, NoSuchAlgorithmException;

    User login(UserDto userDto) throws UnsupportedEncodingException, NoSuchAlgorithmException;

    User selectById(String userId);

    boolean updateStatus(Integer status, String id);

    PageInfo selectUserByName(int page, int limit, String name);
}