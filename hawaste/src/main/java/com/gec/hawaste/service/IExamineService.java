package com.gec.hawaste.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gec.hawaste.entity.Examine;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gec.hawaste.vo.ExamineVo;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author GARCHENG
 * @since 2022-10-12
 */
public interface IExamineService extends IService<Examine> {

    public IPage<ExamineVo> selectExamineByCondition(IPage<ExamineVo> page, Map<String,Object> map);

}
