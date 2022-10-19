package com.gec.hawaste.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.gec.hawaste.entity.Statute;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author GARCHENG
 * @since 2022-10-12
 */
public interface IStatuteService extends IService<Statute> {
    public IPage<Statute> selectStatuteByCondition(IPage<Statute> page,Integer type);

}
