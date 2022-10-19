package com.gec.hawaste.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.gec.hawaste.entity.WorkOrder;
import com.gec.hawaste.mapper.DetailMapper;
import com.gec.hawaste.mapper.TransferMapper;
import com.gec.hawaste.mapper.WorkOrderMapper;
import com.gec.hawaste.service.IWorkOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gec.hawaste.vo.DetailVo;
import com.gec.hawaste.vo.TransferVo;
import com.gec.hawaste.vo.WorkOrderDetailVo;
import com.gec.hawaste.vo.WorkOrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author GARCHENG
 * @since 2022-10-12
 */
@Service
public class WorkOrderServiceImpl extends ServiceImpl<WorkOrderMapper, WorkOrder> implements IWorkOrderService {
    @Autowired
    private TransferMapper transferMapper;
    @Autowired
    private DetailMapper detailMapper;

    @Override
    public IPage<WorkOrderVo> selectWorkOrderByCondition(IPage<WorkOrderVo> page, Map<String, Object> map) {

        QueryWrapper<WorkOrderVo> queryWrapper = new QueryWrapper<>();
        queryWrapper.apply("wo.del_flag = 0 ")
                .eq(map.containsKey("officeId")&&!ObjectUtils.isEmpty(map.get("officeId")),"o.id",map.get("officeId"))
                .eq(map.containsKey("status")&&!ObjectUtils.isEmpty(map.get("status")),"wo.`status`",map.get("status"))
                .between(map.containsKey("startDate")&&map.containsKey("endDate")&&
                        !ObjectUtils.isEmpty(map.get("startDate"))&&
                        !ObjectUtils.isEmpty(map.get("endDate")),"DATE( wo.create_date )",map.get("startDate"),map.get("endDate"));



        return this.baseMapper.selectWorkOrderByCondition(page,queryWrapper);
    }

    @Override
    public WorkOrderDetailVo selectDetailById(Integer id) {
        WorkOrderDetailVo workOrderDetailVo = this.baseMapper.selectDetailById(id);

        List<DetailVo> detailVos = detailMapper.selectDetailVosById(id);
        workOrderDetailVo.setDetails(detailVos);

        List<TransferVo> transferVos = transferMapper.selectTransferVoById(id);
        workOrderDetailVo.setTransfers(transferVos);
        return workOrderDetailVo;
    }
}
