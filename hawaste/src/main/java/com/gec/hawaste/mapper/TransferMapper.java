package com.gec.hawaste.mapper;

import com.gec.hawaste.entity.Transfer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gec.hawaste.vo.TransferVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author GARCHENG
 * @since 2022-10-12
 */
public interface TransferMapper extends BaseMapper<Transfer> {

    @Select("SELECT\n" +
            "t.*,\n" +
            "u.`name` user_name,\n" +
            "u.phone user_phone\n" +
            "FROM \n" +
            "transfer t\n" +
            "LEFT JOIN sys_user u\n" +
            "ON t.oprate_user_id = u.id\n" +
            "WHERE t.del_flag = 0\n" +
            "AND t.work_order_id = #{id} " +
            "order by t.create_date desc")
    List<TransferVo> selectTransferVoById(@Param("id") Integer id);

}
