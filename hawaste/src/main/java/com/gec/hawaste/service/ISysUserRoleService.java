package com.gec.hawaste.service;

import com.gec.hawaste.entity.SysUserRole;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author GARCHENG
 * @since 2022-10-12
 */
public interface ISysUserRoleService extends IService<SysUserRole> {

    void deleteBatchByRidAndIds(String rid, String[] ids);

    void insertBatchByRidAndIds(String rid, String[] ids);
}
