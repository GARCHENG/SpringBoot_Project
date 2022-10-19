package com.gec.hawaste.controller;


import com.gec.hawaste.entity.AppVersion;
import com.gec.hawaste.entity.ResultBean;
import com.gec.hawaste.service.IAppVersionService;
import com.gec.hawaste.utils.PageInfo;
import freemarker.core.ReturnInstruction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author GARCHENG
 * @since 2022-10-12
 */
@RestController
@RequestMapping("/manager/app/")
public class AppVersionController {

    @Autowired
    private IAppVersionService iAppVersionService;

    @RequestMapping("select")
    public ResultBean<AppVersion> select(int current,int size){
        PageInfo<AppVersion> pageInfo = iAppVersionService.page(new PageInfo<AppVersion>(current, size));
        pageInfo.setNavigatePage();
        return ResultBean.ok(pageInfo);
    }


    @RequestMapping("selectOne")
    public ResultBean<AppVersion> selectOne(String id){
        AppVersion appVersion = iAppVersionService.getById(id);
        return ResultBean.ok(appVersion);
    }

    @RequestMapping("saveOrUpdate")
    public ResultBean saveOrUpdate(@RequestBody AppVersion appVersion){
        iAppVersionService.saveOrUpdate(appVersion);
        return ResultBean.ok();
    }

}
