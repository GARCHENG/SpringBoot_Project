package com.gec.hawaste.mapper;

import com.gec.hawaste.entity.SysOffice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 机构表 Mapper 接口
 * </p>
 *
 * @author GARCHENG
 * @since 2022-10-12
 */
public interface SysOfficeMapper extends BaseMapper<SysOffice> {

    @Select("SELECT  " +
            "sof.* " +
            "FROM " +
            "sys_role sr, " +
            "sys_role_office sro, " +
            "sys_office sof " +
            "WHERE " +
            "sr.del_flag = 0 " +
            "AND sro.del_flag = 0 " +
            "AND sof.del_flag = 0 " +
            "AND sr.id = sro.role_id " +
            "AND sro.office_id = sof.id " +
            "AND sr.id = #{rid}")
    List<SysOffice> selectOfficesByRid(@Param("rid") Integer rid);

}
