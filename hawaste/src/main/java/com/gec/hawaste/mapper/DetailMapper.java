package com.gec.hawaste.mapper;

import com.gec.hawaste.entity.Detail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gec.hawaste.vo.DetailVo;
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
public interface DetailMapper extends BaseMapper<Detail> {
    @Select("SELECT\n" +
            "d.*,\n" +
            "w.`code` waste_code,\n" +
            "wt.`name` waste_type_name,\n" +
            "wt.`code` waste_type_code\n" +
            "FROM\n" +
            "detail d\n" +
            "LEFT JOIN waste_type wt\n" +
            "ON d.waste_type_id = wt.id\n" +
            "LEFT JOIN waste w\n" +
            "ON d.waste_id = w.id\n" +
            "\n" +
            "WHERE d.del_flag=0\n" +
            "AND d.work_order_id = #{id}")
    List<DetailVo> selectDetailVosById(@Param("id") Integer id);

}
