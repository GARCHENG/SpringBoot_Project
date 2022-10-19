package com.gec.hawaste.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gec.hawaste.entity.Qualification;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gec.hawaste.vo.QualificationVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author GARCHENG
 * @since 2022-10-12
 */
public interface QualificationMapper extends BaseMapper<Qualification> {


    @Select("SELECT " +
            "q.*, " +
            "u.`name` user_name, " +
            "cu.`name` check_user_name " +
            "FROM " +
            "qualification q " +
            "LEFT JOIN sys_user u " +
            "ON q.upload_user_id = u.id " +
            "LEFT JOIN sys_user cu " +
            "ON q.check_user_id = cu.id ${ew.customSqlSegment}")
    IPage<QualificationVo> selectQualificationVoByCondition(IPage<QualificationVo> page,
                                                            @Param("ew") Wrapper ew);

    @Update("UPDATE qualification  SET  `check`=#{check} WHERE id=#{id}  AND del_flag='0'")
    int updateCheckById(Qualification qualification);


}
