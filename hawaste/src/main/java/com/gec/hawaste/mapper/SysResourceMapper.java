package com.gec.hawaste.mapper;

import com.gec.hawaste.entity.SysResource;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author GARCHENG
 * @since 2022-10-12
 */
public interface SysResourceMapper extends BaseMapper<SysResource> {


    @Select("SELECT " +
            "sre.* " +
            "FROM " +
            "sys_role sr, " +
            "sys_role_resource srr, " +
            "sys_resource sre " +
            "WHERE " +
            "srr.del_flag = 0 " +
            "AND sr.del_flag = 0 " +
            "AND sre.del_flag = 0 " +
            "AND sr.id = srr.role_id " +
            "AND srr.resource_id = sre.id " +
            "AND srr.role_id = #{rid}")
    List<SysResource> selectResourceByRid(@Param("rid") Integer rid);


}
