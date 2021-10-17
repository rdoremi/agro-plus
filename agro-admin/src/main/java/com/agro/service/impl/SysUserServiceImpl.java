package com.agro.service.impl;

import com.agro.mapper.SysRoleMapper;
import com.agro.mapper.SysUserMapper;
import com.agro.pojo.entity.SysRole;
import com.agro.pojo.entity.SysRoleUser;
import com.agro.pojo.entity.SysUser;
import com.agro.pojo.dto.SysUserDto;
import com.agro.pojo.entity.SysUser;
import com.agro.result.Const;
import com.agro.service.SysRoleUserService;
import com.agro.service.SysUserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service("sysUserService")
@Slf4j
@Transactional
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserMapper sysUserDao;
    @Autowired
    private SysRoleUserService roleUserDao;
    @Autowired
    private SysRoleMapper sysRoleMapper;


    @Override
    public SysUser queryById(Long id) {
        return this.sysUserDao.selectByPrimaryKey(id);
    }


    @Override
    public PageInfo queryAllByLimit(int offset, int limit) {
        List<SysUser> sysUserList = sysUserDao.selectAll();

        List<SysUserDto> userDtoList = new ArrayList<>();
        for (SysUser user : sysUserList) {
            SysUserDto userDto = new SysUserDto();



            SysRoleUser sysRoleUser = roleUserDao.queryById(user.getId());

            SysRole sysRole = sysRoleMapper.selectByPrimaryKey(sysRoleUser.getRoleId());
//            Example example = new Example(SysRole.class);
//            example.createCriteria().andEqualTo("id",sysRoleUser.getRoleId());
//            SysRole sysRole = sysRoleMapper.selectOneByExample(example);
            BeanUtils.copyProperties(user,userDto);
            userDto.setRoleName(sysRole.getName());
            userDtoList.add(userDto);
        }

        PageHelper.startPage(offset, limit);
        PageInfo pageInfo = new PageInfo(userDtoList);
        //pageInfo.setList(sysUserList);

        return pageInfo;
    }




    @Override
    public SysUserDto insert(SysUserDto userDto) throws UnsupportedEncodingException, NoSuchAlgorithmException {


        SysUser sysUser = new SysUser();
        /*sysUser.setNickname(userDto.getNickname());
        sysUser.setEmail(userDto.getEmail());
        sysUser.setPhone(userDto.getPhone());
        sysUser.setSex(userDto.getSex());
        sysUser.setUsername(userDto.getUsername());
        sysUser.setStatus(Const.Status.VALID);*/
        BeanUtils.copyProperties(userDto, sysUser);
        sysUser.setStatus(Const.Status.VALID);
        sysUser.setCreateTime(new Date());
        sysUser.setHeadimg("https://famer-web.oss-cn-chengdu.aliyuncs.com/headImages/1/451493c9cb2c4004b018af190eb3c59bavatar-1.jpg");
        //sysUser.setPassword(Md5.EncoderByMd5(userDto.getPassword()));
        log.info("添加的用户：" + sysUser);

        sysUser.setPassword(new BCryptPasswordEncoder().encode(userDto.getPassword()));
        int rs = this.sysUserDao.insert(sysUser);
        if (rs > 0) {
            Example example = new Example(SysUser.class);
            example.createCriteria().andEqualTo("phone",sysUser.getPhone());
            SysUser user = sysUserDao.selectOneByExample(example);
//            SysUser user1 = sysUserDao.selectByPrimaryKey(sysUser.getPhone());
            SysRoleUser sysRoleUser = new SysRoleUser();
            sysRoleUser.setUserId(user.getId());
            sysRoleUser.setRoleId(userDto.getRoleId());
//            sysRoleUser.setRoleId(6);
            int rls = roleUserDao.save(sysRoleUser);
            return rls > 0 ? userDto : null;
        }

        return null;
    }


    @Override
    public Boolean update(SysUserDto userDto) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        //this.sysUserDao.updateByPrimaryKey(sysUser);
        SysUser user = new SysUser();

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//        auth.userDetailsService(userDetailsService).passwordEncoder(encoder);

//        user.setPassword(Md5.EncoderByMd5(userDto.getPassword())); $2a$10$oMlLnDs4wh6PfoPj9WoXhODTTvOSL5/gR2kPUMMqlC.vFCGm8XiPG
        String encode = encoder.encode(userDto.getPassword());
        System.out.println(encode);
        user.setPassword(encode);
        user.setNickname(userDto.getNickname());
        user.setEmail(userDto.getEmail());
        user.setSex(userDto.getSex());
        user.setId(userDto.getId());
        System.out.println(user);
        Example example = new Example(SysUser.class);
        example.createCriteria().andEqualTo("id",userDto.getId());
        int i = sysUserDao.updateByExampleSelective(user,example);
        SysRoleUser roleUser = new SysRoleUser();
        roleUser.setUserId(userDto.getId());
        roleUser.setRoleId(userDto.getRoleId());
        Boolean update = roleUserDao.update(roleUser);

        if (i > 0 && update) {
            return true;
        }
        return false;
    }

    @Override
    public boolean deleteById(Long id) {

        int rs = sysUserDao.deleteByPrimaryKey(id);
        return rs > 0;
    }

    @Override
    public SysUser getUser(String phone) {
        Example example = new Example(SysUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("phone", phone);
        SysUser sysUser = sysUserDao.selectOneByExample(example);

        return sysUser;
    }

    @Override
    public boolean queryUserIsExist(String phone) {
        SysUser sysUser = sysUserDao.selectByPrimaryKey(phone);
        return sysUser == null ? false : true;
    }

    @Override
    public boolean updateStatus(Integer status, Long id) {
        Example example = new Example(SysUser.class);
        example.createCriteria().andEqualTo("id",id);
        SysUser user = new SysUser();
        user.setStatus(status);
        user.setId(id);
        int i = sysUserDao.updateByExampleSelective(user,example);
        return i>0?true:false;
    }



    @Override
    public PageInfo selectUserByName(int page, int limit, String name) {
        Example example = new Example(SysUser.class);
        Example.Criteria criteria = example.createCriteria();
        example.setOrderByClause("create_time DESC");
        criteria.orLike("username","%" +name+"%")
                .orLike("nickname","%" +name+"%")
                .orLike("phone","%" +name+"%");
        List<SysUser> users = sysUserDao.selectByExample(example);
        PageHelper.startPage(page,limit);
        PageInfo pageInfo = new PageInfo(users);
        return pageInfo;
    }
}