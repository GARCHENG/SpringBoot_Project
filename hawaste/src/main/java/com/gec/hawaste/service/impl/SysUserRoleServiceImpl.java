package com.gec.hawaste.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gec.hawaste.entity.SysUserRole;
import com.gec.hawaste.mapper.SysUserRoleMapper;
import com.gec.hawaste.service.ISysUserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.ietf.jgss.Oid;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author GARCHENG
 * @since 2022-10-12
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements ISysUserRoleService {

    @Override
    public void deleteBatchByRidAndIds(String rid, String[] ids) {
        List<String> list = Arrays.asList(ids);

        QueryWrapper<SysUserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id", rid)
                .in("user_id",list);

        this.remove(queryWrapper);

    }

    @Override
    public void insertBatchByRidAndIds(String rid, String[] ids) {
        if (ids != null) {
            for (String id : ids) {
                this.baseMapper.insert(new SysUserRole(Long.parseLong(rid+""),Long.parseLong(id+""),"0"));
            }
        }

    }
}
