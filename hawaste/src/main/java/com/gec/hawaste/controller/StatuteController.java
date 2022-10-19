package com.gec.hawaste.controller;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.gec.hawaste.entity.ResultBean;
import com.gec.hawaste.entity.Statute;
import com.gec.hawaste.service.IStatuteService;
import com.gec.hawaste.utils.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
@RequestMapping("/manager/statute/")
public class StatuteController {

//    url:`/manager/statute/select/${pageNum}/${pageSize}`,

    @Autowired
    private IStatuteService iStatuteService;

    @RequestMapping("select/{pageNum}/{pageSize}")
    public ResultBean<PageInfo> select(@PathVariable("pageNum") int pageNum,
                                       @PathVariable("pageSize") int pageSize,
                                       @RequestParam("type") Integer type){

//        PageInfo<Statute> pageInfo = new PageInfo<>(pageNum,pageSize);
//
//        QueryWrapper<Statute> queryWrapper= new QueryWrapper<>();
//        if (map.containsKey("type")&&!ObjectUtils.isEmpty(map.get("type"))){
//
//            queryWrapper.eq("type",map.get("type"));
//        }
//
//        pageInfo = iStatuteService.page(pageInfo, queryWrapper);
//
//        pageInfo.setNavigatePage();
//
//        return ResultBean.ok(pageInfo);

        PageInfo<Statute> pageInfo = new PageInfo<>(pageNum,pageSize);

        pageInfo = (PageInfo<Statute>) iStatuteService.selectStatuteByCondition(pageInfo,type);

        pageInfo.setNavigatePage();

        return ResultBean.ok(pageInfo);



    }


    @RequestMapping("selectOne")
    public ResultBean<Statute> selectOne(String id){

        Statute statute = iStatuteService.getById(id);

        return ResultBean.ok(statute);


    }


    @RequestMapping("saveOrUpdate")
    public ResultBean saveOrUpdate(@RequestBody Statute statute){
        iStatuteService.saveOrUpdate(statute);
        return ResultBean.ok();

    }

    @RequestMapping("deleteOne")
    public ResultBean deleteOne(String id){
        iStatuteService.removeById(id);
        return ResultBean.ok();

    }

}
