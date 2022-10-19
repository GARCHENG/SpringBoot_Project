package com.gec.hawaste.controller;


import com.gec.hawaste.entity.ResultBean;
import com.gec.hawaste.entity.WorkOrder;
import com.gec.hawaste.service.IWorkOrderService;
import com.gec.hawaste.utils.PageInfo;
import com.gec.hawaste.vo.WorkOrderDetailVo;
import com.gec.hawaste.vo.WorkOrderVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author GARCHENG
 * @since 2022-10-12
 */

@RestController
@RequestMapping("/manager/work/")
public class WorkOrderController {

    @Autowired
    private IWorkOrderService iWorkOrderService;

//    url:`/manager/work/select/${current}/${size}`,
    @RequestMapping("select/{current}/{size}")
    public ResultBean<PageInfo> select(@PathVariable("current") Integer current,
                                       @PathVariable("size") Integer size,
                                       @RequestParam Map<String,Object> map){


        PageInfo<WorkOrderVo> pageInfo = new PageInfo<>(current, size);

        pageInfo = (PageInfo<WorkOrderVo>) iWorkOrderService.selectWorkOrderByCondition(pageInfo,map);

        pageInfo.setNavigatePage();

        return ResultBean.ok(pageInfo);



    }


    @RequestMapping("selectOne/{oid}")
    public ResultBean<WorkOrder> selectOne(@PathVariable("oid") String oid){
        WorkOrder workOrder = iWorkOrderService.getById(oid);
        return ResultBean.ok(workOrder);

    }

    @RequestMapping("showDetails")
    public ResultBean<WorkOrderDetailVo> showDetails(Integer id){
        WorkOrderDetailVo workOrderDetailVo = iWorkOrderService.selectDetailById(id);
        return ResultBean.ok(workOrderDetailVo);
    }



}
