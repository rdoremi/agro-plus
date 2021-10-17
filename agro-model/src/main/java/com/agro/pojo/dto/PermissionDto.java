package com.agro.pojo.dto;


import com.agro.pojo.entity.SysPermission;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Accessors(chain = true)
public class PermissionDto {
    private int id;
    private String name;
    private List<SysPermission> cList;

}
