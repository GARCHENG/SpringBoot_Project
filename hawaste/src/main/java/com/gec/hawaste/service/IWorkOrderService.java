package com.gec.hawaste.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gec.hawaste.entity.WorkOrder;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gec.hawaste.vo.WorkOrderDetailVo;
import com.gec.hawaste.vo.WorkOrderVo;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author GARCHENG
 * @since 2022-10-12
 */
public interface IWorkOrderService extends IService<WorkOrder> {

    public IPage<WorkOrderVo> selectWorkOrderByCondition(IPage<WorkOrderVo> page, Map<String,Object> map);

    WorkOrderDetailVo selectDetailById(Integer id);

}
