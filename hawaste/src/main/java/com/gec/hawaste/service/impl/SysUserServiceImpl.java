package com.gec.hawaste.service.impl;

import com.gec.hawaste.entity.SysUser;
import com.gec.hawaste.mapper.SysUserMapper;
import com.gec.hawaste.service.ISysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author GARCHENG
 * @since 2022-10-12
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {

    @Override
    public List<SysUser> selectUserByRid(Integer rid) {
        return this.baseMapper.selectUserByRid(rid);
    }

    @Override
    public List<SysUser> selectNoRoleByOidAndRid(Integer oid, Integer rid) {

        return this.baseMapper.selectNoRoleByOidAndRid(oid,rid);
    }
}
