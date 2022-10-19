package com.gec.hawaste.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.gec.hawaste.entity.Examine;
import com.gec.hawaste.mapper.ExamineMapper;
import com.gec.hawaste.service.IExamineService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gec.hawaste.vo.ExamineVo;
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
public class ExamineServiceImpl extends ServiceImpl<ExamineMapper, Examine> implements IExamineService {

//    AND examine.type=1
//    AND sys_office.id = 56
//    AND sys_user.`name` LIKE '%人员%'
    @Override
    public IPage<ExamineVo> selectExamineByCondition(IPage<ExamineVo> page, Map<String, Object> map) {
        QueryWrapper<ExamineVo> queryWrapper = new QueryWrapper<>();

        queryWrapper.apply("examine.del_flag=0 " +
                "AND examine.examine_user_id = sys_user.id " +
                "AND sys_user.office_id = sys_office.id")
                .eq(map.containsKey("type")&&!ObjectUtils.isEmpty(map.get("type")),"examine.type",map.get("type"))
                .eq(map.containsKey("officeId")&&!ObjectUtils.isEmpty(map.get("officeId")),"sys_office.id",map.get("officeId"))
                .like(map.containsKey("name")&&!ObjectUtils.isEmpty(map.get("name")),"sys_user.`name`",map.get("name"));


        return this.baseMapper.selectExamineByCondition(page,queryWrapper);
    }
}
