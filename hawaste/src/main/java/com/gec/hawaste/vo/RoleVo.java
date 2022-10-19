package com.gec.hawaste.vo;

import com.gec.hawaste.entity.SysOffice;
import com.gec.hawaste.entity.SysResource;
import com.gec.hawaste.entity.SysRole;
import lombok.Data;

import java.util.List;

@Data
public class RoleVo extends SysRole {

    private String officeName;

    private List<SysResource> resources;

    private List<SysOffice> offices;

}
