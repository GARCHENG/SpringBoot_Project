package com.gec.hawaste.service.impl;

import com.gec.hawaste.entity.Demand;
import com.gec.hawaste.mapper.DemandMapper;
import com.gec.hawaste.service.IDemandService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 客户的需求填写 服务实现类
 * </p>
 *
 * @author GARCHENG
 * @since 2022-10-12
 */
@Service
public class DemandServiceImpl extends ServiceImpl<DemandMapper, Demand> implements IDemandService {

}
