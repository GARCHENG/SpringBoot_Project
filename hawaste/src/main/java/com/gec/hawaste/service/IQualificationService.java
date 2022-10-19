package com.gec.hawaste.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gec.hawaste.entity.Qualification;
import com.baomidou.mybatisplus.extension.service.IService;
import com.gec.hawaste.vo.QualificationVo;
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
public interface IQualificationService extends IService<Qualification> {

    IPage<QualificationVo> selectQualificationVoByCondition(IPage<QualificationVo> page,
                                                            Map<String,Object> map);

    Qualification selectQualificationById(Integer id);

    void updateCheckById(Qualification qualification);
}
