package com.gec.hawaste.service;

import com.gec.hawaste.entity.SysUser;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author GARCHENG
 * @since 2022-10-12
 */
public interface ISysUserService extends IService<SysUser> {

    List<SysUser> selectUserByRid(Integer rid);

    List<SysUser> selectNoRoleByOidAndRid(Integer oid, Integer rid);

}
