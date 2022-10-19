package com.gec.hawaste.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gec.hawaste.entity.ResultBean;
import com.gec.hawaste.service.IExamineService;
import com.gec.hawaste.utils.PageInfo;
import com.gec.hawaste.vo.ExamineVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
@RequestMapping("/manager/examine/")
public class ExamineController {


//    url:`/manager/examine/select/${current}/${size}`,
    @Autowired
    private IExamineService iExamineService;

    @RequestMapping("select/{current}/{size}")
    public ResultBean<PageInfo> select(@PathVariable("current") int current,
                                       @PathVariable("size") int size,
                                       @RequestParam Map<String,Object> params) {

        PageInfo<ExamineVo> pageInfo = new PageInfo<>(current,size);
         pageInfo = (PageInfo<ExamineVo>) iExamineService.selectExamineByCondition(pageInfo, params);
         pageInfo.setNavigatePage();
         return ResultBean.ok(pageInfo);

    }


}
