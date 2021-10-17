package com.agro.service.impl;


import com.agro.mapper.UserMapper;
import com.agro.org.n3r.idworker.Sid;
import com.agro.pojo.dto.UserDto;
import com.agro.pojo.entity.User;
import com.agro.result.Const;
import com.agro.service.UserService;
import com.agro.utils.Md5;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

/**
 * (User)表服务实现类
 *
 * @author makejava
 * @since 2020-07-25 14:13:25
 */
@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public User queryById(Long id) {
        return this.userDao.selectByPrimaryKey(id);
    }

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    @Override
    public PageInfo<User> queryAllByLimit(int offset, int limit) {
        List<User> userList = userDao.selectAll();
        Page page = PageHelper.startPage(offset, limit);
        PageInfo pageInfo = new PageInfo(page);
        pageInfo.setList(userList);
        return pageInfo;
    }

    /**
     * 新增数据
     *
     * @param user 实例对象
     * @return 实例对象
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public User insert(User user) throws UnsupportedEncodingException, NoSuchAlgorithmException {

        Sid sid = new Sid();
        user.setId(sid.next());
        user.setHeadImg("assets/images/avatars/avatar-1.jpg");
        user.setPassword(Md5.EncoderByMd5(user.getPassword()));
        user.setSex(1);
        int rs = this.userDao.insert(user);
        return rs > 0 ? user : null;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public User wxRegester(User user) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        user.setTel("");
        user.setPassword(Md5.EncoderByMd5("000000"));
        user.setSex(1);
        user.setStatus(Const.Status.VALID);
        int rs = this.userDao.insert(user);
        return rs > 0 ? user : null;
    }

    @Override
    public User login(UserDto userDto) throws UnsupportedEncodingException, NoSuchAlgorithmException {


        Example example = new Example(User.class);
        Example.Criteria userCriteria = example.createCriteria();
        userCriteria.andEqualTo("tel",userDto.getTel());
        userCriteria.andEqualTo("password", Md5.EncoderByMd5(userDto.getPassword()));
        User rs = userDao.selectOneByExample(example);
        return rs;
    }

    /**
     * 判断用户是否存在
     * @param tel
     * @return
     */

    @Override
    public boolean queryUserIsExist(String tel) {
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("tel",tel);
        User user = userDao.selectOneByExample(example);
        return user == null ? true : false;
    }

    @Override
    public boolean update(User user) {
        user.setUpdateTime(new Date());
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("id",user.getId());
        int i = userDao.updateByExampleSelective(user,example);
        return i>0?true:false;
    }

    /**
     * 修改数据
     *
     * @param user 实例对象
     * @return 实例对象
     */


    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String id) {
        return this.userDao.deleteByPrimaryKey(id) > 0;
    }

    @Override
    public User selectById(String userId) {
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("id",userId);
        return userDao.selectOneByExample(example);
    }

    @Override
    public boolean updateStatus(Integer status, String id) {
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("id",id);
        User user = new User();
        user.setStatus(status);
        user.setId(id);
        int i = userDao.updateByExampleSelective(user,example);
        return i>0?true:false;
    }

    @Override
    public PageInfo selectUserByName(int page, int limit, String name) {
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        example.setOrderByClause("create_time DESC");
        criteria.orLike("username","%" +name+"%")
                .orLike("tel","%" +name+"%");
        List<User> userList = userDao.selectByExample(example);
        PageHelper.startPage(page,limit);
        PageInfo pageInfo = new PageInfo(userList);
        return pageInfo;
    }
}