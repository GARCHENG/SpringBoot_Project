package com.gec.hawaste.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gec.hawaste.entity.WorkOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gec.hawaste.vo.WorkOrderDetailVo;
import com.gec.hawaste.vo.WorkOrderVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author GARCHENG
 * @since 2022-10-12
 */
public interface WorkOrderMapper extends BaseMapper<WorkOrder> {

    @Select("SELECT " +
            "wo.*, " +
            "cu.`name` create_user_name, " +
            "tu.`name` transport_user_name, " +
            "ru.`name` recipient_user_name, " +
            "o.`name` create_office_name " +
            "FROM " +
            "work_order wo " +
            "LEFT JOIN sys_user cu " +
            "ON wo.create_user_id = cu.id " +
            "LEFT JOIN sys_office o " +
            "ON cu.office_id = o.id " +
            "LEFT JOIN sys_user tu " +
            "ON wo.transport_user_id = tu.id " +
            "LEFT JOIN sys_user ru " +
            "ON wo.recipient_user_id = ru.id ${ew.customSqlSegment}")
    public IPage<WorkOrderVo> selectWorkOrderByCondition(IPage<WorkOrderVo> page, @Param("ew") Wrapper ew);


    @Select("SELECT " +
            "wo.*, " +
            "cu.`name` create_user_name, " +
            "co.`name` create_office_name, " +
            "cu.phone create_user_phone, " +
            "tu.`name` transport_user_name, " +
            "`to`.`name` transport_office_name, " +
            "tu.phone transport_user_phone, " +
            "ru.`name` recipient_user_name, " +
            "ro.`name` recipient_office_name, " +
            "ru.phone recipient_user_phone " +
            "FROM " +
            "work_order wo " +
            "LEFT JOIN sys_user cu " +
            "ON wo.create_user_id = cu.id " +
            "LEFT JOIN sys_office co " +
            "ON cu.office_id = co.id " +
            "LEFT JOIN sys_user tu " +
            "ON wo.transport_user_id = tu.id " +
            "LEFT JOIN sys_office `to` " +
            "ON tu.office_id = to.id " +
            "LEFT JOIN sys_user ru " +
            "ON wo.recipient_user_id = ru.id " +
            "LEFT JOIN sys_office ro " +
            "ON ru.office_id = ro.id " +
            "WHERE wo.id = #{id} " +
            "and wo.del_flag = 0")
    WorkOrderDetailVo selectDetailById(@Param("id") Integer id);

}
