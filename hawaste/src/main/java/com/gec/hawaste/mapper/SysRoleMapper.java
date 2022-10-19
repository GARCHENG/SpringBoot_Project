package com.gec.hawaste.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gec.hawaste.entity.SysRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gec.hawaste.vo.RoleVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author GARCHENG
 * @since 2022-10-12
 */
public interface SysRoleMapper extends BaseMapper<SysRole> {

    @Select("SELECT  " +
            "sr.*, " +
            "so.`name` office_name " +
            "FROM " +
            "sys_role sr " +
            "LEFT JOIN sys_office so " +
            "ON sr.office_id = so.id ${ew.customSqlSegment}")
    IPage<RoleVo> selectRoleVoByCondition(IPage<RoleVo> page, @Param("ew") Wrapper ew);

    @Select("SELECT  " +
            "sr.*, " +
            "so.`name` office_name " +
            "FROM " +
            "sys_role sr " +
            "LEFT JOIN sys_office so " +
            "ON sr.office_id = so.id " +
            "Where sr.id = #{id}")
    RoleVo selectRoleVoById(@Param("id") Integer id);

}
