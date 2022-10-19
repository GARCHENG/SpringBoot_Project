package com.gec.hawaste.controller;


import com.gec.hawaste.entity.ResultBean;
import com.gec.hawaste.entity.SysResource;
import com.gec.hawaste.service.ISysResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author GARCHENG
 * @since 2022-10-12
 */
@RestController
@RequestMapping("/manager/menu/")
public class SysResourceController {

    @Autowired
    private ISysResourceService iSysResourceService;

//    url:'/manager/menu/list'

    @RequestMapping("list")
    public ResultBean<List> list(){
        List<SysResource> list = iSysResourceService.list();
        return ResultBean.ok(list);
    }


}
