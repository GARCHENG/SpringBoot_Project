package com.gec.hawaste.service.impl;

import com.gec.hawaste.entity.SysOffice;
import com.gec.hawaste.mapper.SysOfficeMapper;
import com.gec.hawaste.service.ISysOfficeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 机构表 服务实现类
 * </p>
 *
 * @author GARCHENG
 * @since 2022-10-12
 */
@Service
public class SysOfficeServiceImpl extends ServiceImpl<SysOfficeMapper, SysOffice> implements ISysOfficeService {

    @Override
    public List<SysOffice> selectOfficesByRid(Integer rid) {
        return this.baseMapper.selectOfficesByRid(rid);
    }
}
