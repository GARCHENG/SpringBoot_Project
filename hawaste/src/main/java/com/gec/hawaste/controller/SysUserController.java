package com.gec.hawaste.controller;


import com.gec.hawaste.entity.ResultBean;
import com.gec.hawaste.entity.SysUser;
import com.gec.hawaste.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author GARCHENG
 * @since 2022-10-12
 */
@RestController
@RequestMapping("/manager/user/")
public class SysUserController {


    @Autowired
    private ISysUserService iSysUserService;

//    url:`/manager/user/selectByRid/${this.role.id}`

    @RequestMapping("selectByRid/{roleid}")
    public ResultBean<List> selectByRid(@PathVariable("roleid") Integer roleid){

        List<SysUser> users = iSysUserService.selectUserByRid(roleid);

        return ResultBean.ok(users);


    }

//    url:`/manager/user/selectNoRole/${this.role.id}/${oid}`
    @RequestMapping("selectNoRole/{rid}/{oid}")
    public ResultBean<List> selectNoRole(@PathVariable("rid") Integer rid,
                                         @PathVariable("oid") Integer oid){

        List<SysUser> users = iSysUserService.selectNoRoleByOidAndRid(oid, rid);
        return ResultBean.ok(users);


    }




}
