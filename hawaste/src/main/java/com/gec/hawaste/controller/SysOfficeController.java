package com.gec.hawaste.controller;


import com.gec.hawaste.entity.ResultBean;
import com.gec.hawaste.entity.SysOffice;
import com.gec.hawaste.service.ISysOfficeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 机构表 前端控制器
 * </p>
 *
 * @author GARCHENG
 * @since 2022-10-12
 */
@RestController
@RequestMapping("/manager/office/")
public class SysOfficeController {

    @Autowired
    private ISysOfficeService iSysOfficeService;
//    url:'/manager/office/selectAll'

    @RequestMapping("selectAll")
    public ResultBean<List> selectAll(){
        List<SysOffice> list = iSysOfficeService.list();
        return ResultBean.ok(list);

    }

}
