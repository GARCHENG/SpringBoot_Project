package com.gec.hawaste.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.gec.hawaste.entity.Qualification;
import com.gec.hawaste.mapper.QualificationMapper;
import com.gec.hawaste.service.IQualificationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gec.hawaste.vo.QualificationVo;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author GARCHENG
 * @since 2022-10-12
 */
@Service
public class QualificationServiceImpl extends ServiceImpl<QualificationMapper, Qualification> implements IQualificationService {

    @Override
    public IPage<QualificationVo> selectQualificationVoByCondition(IPage<QualificationVo> page, Map<String, Object> map) {
//                startDate:'',
//                endDate:'',
//                type:'',
//                check:''

        QueryWrapper<QualificationVo> queryWrapper = new QueryWrapper<>();

        queryWrapper.apply("q.del_flag = 0")
                .eq(map.containsKey("check")&&!ObjectUtils.isEmpty(map.get("check")),"q.`check`",map.get("check"))
                .eq(map.containsKey("type")&&!ObjectUtils.isEmpty(map.get("type")),"q.type",map.get("type"))
                .between(map.containsKey("startDate")&&map.containsKey("endDate")&&
                        !ObjectUtils.isEmpty(map.get("startDate"))&&
                        !ObjectUtils.isEmpty(map.get("endDate")),"DATE( q.create_date )",map.get("startDate"),map.get("endDate"));

        return this.baseMapper.selectQualificationVoByCondition(page,queryWrapper);
    }

    @Override
    public Qualification selectQualificationById(Integer id) {

        QueryWrapper<Qualification> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("*")
                .eq("id",id);


        return this.baseMapper.selectOne(queryWrapper);
    }

    @Override
    public void updateCheckById(Qualification qualification) {
        this.baseMapper.updateCheckById(qualification);

    }
}
