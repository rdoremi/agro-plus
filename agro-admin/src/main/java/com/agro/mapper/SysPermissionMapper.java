package com.agro.mapper;




import com.agro.pojo.entity.SysPermission;
import com.agro.tkmapper.BaseMapper;


import java.util.List;

public interface SysPermissionMapper extends BaseMapper<SysPermission> {
    List<SysPermission> selectListByUserId(Long id);
}