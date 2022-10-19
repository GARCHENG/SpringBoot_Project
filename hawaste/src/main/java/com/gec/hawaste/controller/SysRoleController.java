package com.gec.hawaste.controller;


import com.gec.hawaste.entity.ResultBean;
import com.gec.hawaste.entity.SysRole;
import com.gec.hawaste.service.ISysOfficeService;
import com.gec.hawaste.service.ISysResourceService;
import com.gec.hawaste.service.ISysRoleService;
import com.gec.hawaste.service.ISysUserRoleService;
import com.gec.hawaste.utils.PageInfo;
import com.gec.hawaste.vo.RoleVo;
import org.junit.validator.PublicClassValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author GARCHENG
 * @since 2022-10-12
 */
@RestController
@RequestMapping("/manager/role/")
public class SysRoleController {


    @Autowired
    private ISysUserRoleService iSysUserRoleService;
    @Autowired
    private ISysRoleService iSysRoleService;


//    url:`/manager/role/select/${current}/${size}`,

    @RequestMapping("select/{current}/{size}")
    public ResultBean<PageInfo> select(@PathVariable("current") Integer current,
                                       @PathVariable("size") Integer size,
                                       @RequestParam Map<String,Object> map){

        PageInfo<RoleVo> pageInfo = new PageInfo<>(current,size);

        pageInfo = (PageInfo<RoleVo>) iSysRoleService.selectRoleVoByCondition(pageInfo,map);

        pageInfo.setNavigatePage();

        return ResultBean.ok(pageInfo);


    }

//    url:'/manager/role/deleteBatch',
//    params:{rid:this.role.id,ids:this.yxIds+''}

    @RequestMapping("deleteBatch")
    public ResultBean deleteBatch(String rid,String []ids){

        iSysUserRoleService.deleteBatchByRidAndIds(rid,ids);
        return ResultBean.ok();

    }

//    url:'/manager/role/insertBatch',
//    params:{rid:this.role.id,ids:this.dxIds+''}
    @RequestMapping("insertBatch")
    public ResultBean insertBatch(String rid,String []ids){

        iSysUserRoleService.insertBatchByRidAndIds(rid,ids);

        return ResultBean.ok();

    }

//    url:'/manager/role/selectOne',

    @RequestMapping("selectOne")
    public ResultBean<RoleVo> selectOne(Integer id){

        RoleVo roleVo = iSysRoleService.selectRoleVoById(id);



        return ResultBean.ok(roleVo);

    }

//           'url':'/manager/role/saveOrUpdate',
    @RequestMapping("saveOrUpdate")
    public ResultBean saveOrUpdate(@RequestBody RoleVo roleVo){

        iSysRoleService.saveOrUpdate(roleVo);
        return ResultBean.ok();

    }



}
