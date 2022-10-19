package com.gec.hawaste.controller;


import com.gec.hawaste.entity.Qualification;
import com.gec.hawaste.entity.ResultBean;
import com.gec.hawaste.service.IQualificationService;
import com.gec.hawaste.utils.PageInfo;
import com.gec.hawaste.vo.QualificationVo;
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
@RequestMapping("/manager/qualification/")
public class QualificationController {

    @Autowired
    private IQualificationService iQualificationService;

//    url:`/manager/qualification/select/${current}/${size}`,

    @RequestMapping("select/{current}/{size}")
    public ResultBean<PageInfo> select(@PathVariable("current") Integer current,
                                       @PathVariable("size") Integer size,
                                       @RequestParam Map<String,Object> map){

        PageInfo<QualificationVo> pageInfo = new PageInfo<>(current,size);

        pageInfo = (PageInfo<QualificationVo>) iQualificationService.selectQualificationVoByCondition(pageInfo,map);

        pageInfo.setNavigatePage();

        return ResultBean.ok(pageInfo);



    }

    @RequestMapping("selectOne/{id}")
    public ResultBean<Qualification> selectOne(@PathVariable("id") Integer id){
//        Qualification qualification = iQualificationService.getById(id);
        Qualification qualification = iQualificationService.selectQualificationById(id);
        return ResultBean.ok(qualification);
    }

    @RequestMapping("updateCheck")
    public  ResultBean updateCheck(@RequestBody Qualification qualification){

        iQualificationService.updateCheckById(qualification);

        return  ResultBean.ok();

    }


}
