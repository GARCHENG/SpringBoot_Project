package com.gec.hawaste.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gec.hawaste.entity.SysRole;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gec.hawaste.vo.RoleVo;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author GARCHENG
 * @since 2022-10-12
 */
public interface ISysRoleService extends IService<SysRole> {

    IPage<RoleVo> selectRoleVoByCondition(IPage<RoleVo> page, Map<String,Object> map);

    RoleVo selectRoleVoById(Integer id);
}
