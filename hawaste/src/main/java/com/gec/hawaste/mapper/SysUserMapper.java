package com.gec.hawaste.mapper;

import com.gec.hawaste.entity.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 用户表 Mapper 接口
 * </p>
 *
 * @author GARCHENG
 * @since 2022-10-12
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    @Select("SELECT\n" +
            "su.*\n" +
            "FROM\n" +
            "sys_user su\n" +
            "LEFT JOIN sys_user_role sur\n" +
            "ON su.id = sur.user_id\n" +
            "WHERE \n" +
            "su.del_flag = 0\n" +
            "AND sur.del_flag = 0\n" +
            "AND sur.role_id = #{rid}")
    List<SysUser> selectUserByRid(@Param("rid") Integer rid);

    @Select("SELECT  " +
            "su.*  " +
            "FROM " +
            "sys_user su " +
            "WHERE su.del_flag = 0 " +
            "AND su.office_id = #{oid} " +
            "AND su.id NOT IN " +
            "(SELECT " +
            "su.id " +
            "FROM " +
            "sys_user su " +
            "LEFT JOIN sys_user_role sur " +
            "ON su.id = sur.user_id " +
            "WHERE  " +
            "su.del_flag = 0 " +
            "AND sur.del_flag = 0 " +
            "AND sur.role_id = #{rid})")
    List<SysUser> selectNoRoleByOidAndRid(@Param("oid") Integer oid,
                                          @Param("rid") Integer rid);


}
