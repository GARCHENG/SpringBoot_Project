package com.gec.hawaste.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gec.hawaste.entity.Examine;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gec.hawaste.vo.ExamineVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author GARCHENG
 * @since 2022-10-12
 */
public interface ExamineMapper extends BaseMapper<Examine> {

    @Select("SELECT  " +
            "examine.*, " +
            "sys_user.`name` AS user_name, " +
            "sys_office.`name` AS office_name " +
            "from " +
            "examine, " +
            "sys_user, " +
            "sys_office ${ew.customSqlSegment}")
    public IPage<ExamineVo> selectExamineByCondition(IPage<ExamineVo> page,@Param("ew") Wrapper ew);

}
