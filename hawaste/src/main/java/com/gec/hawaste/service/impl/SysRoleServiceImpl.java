package com.gec.hawaste.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.gec.hawaste.entity.*;
import com.gec.hawaste.mapper.SysRoleMapper;
import com.gec.hawaste.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gec.hawaste.vo.RoleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author GARCHENG
 * @since 2022-10-12
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements ISysRoleService {

    @Autowired
    private ISysResourceService iSysResourceService;
    @Autowired
    private ISysOfficeService iSysOfficeService;
    @Autowired
    private ISysRoleOfficeService iSysRoleOfficeService;
    @Autowired
    private ISysRoleResourceService iSysRoleResourceService;

    @Override
    public IPage<RoleVo> selectRoleVoByCondition(IPage<RoleVo> page, Map<String, Object> map) {

//        params:{
//                    dataScope:'',//默认值，让下拉出现的时候默认被选中
//                    id:'',
//                    name:'',
//                    remarks:''
//        },

        QueryWrapper<RoleVo> queryWrapper = new QueryWrapper<>();
        queryWrapper.apply("sr.del_flag = 0")
                .eq(map.containsKey("dataScope")&&!ObjectUtils.isEmpty(map.get("dataScope")),"sr.data_scope",map.get("dataScope"))
                .eq(map.containsKey("id")&&!ObjectUtils.isEmpty(map.get("id")),"sr.office_id",map.get("id"))
                .like(map.containsKey("name")&&!ObjectUtils.isEmpty(map.get("name")),"sr.`name`",map.get("name"))
                .like(map.containsKey("remarks")&&!ObjectUtils.isEmpty(map.get("remarks")),"sr.remarks",map.get("remarks"));

        return this.baseMapper.selectRoleVoByCondition(page,queryWrapper);
    }

    @Override
    public RoleVo selectRoleVoById(Integer id) {
        RoleVo roleVo = this.baseMapper.selectRoleVoById(id);

        roleVo.setResources(iSysResourceService.selectResourceByRid(id));

        roleVo.setOffices(iSysOfficeService.selectOfficesByRid(id));

        return roleVo;
    }

    @Override
    @Transactional
    public boolean updateById(SysRole entity) {
        RoleVo roleVo = (RoleVo) entity;

        super.updateById(entity);
        List<SysOffice> offices = roleVo.getOffices();
        if (!ObjectUtils.isEmpty(offices)){
            updateRoleOfficesByRid(offices,entity.getId());
        }else {//取消跨机构授权
            QueryWrapper<SysRoleOffice> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("role_id",entity.getId());
            iSysRoleOfficeService.remove(queryWrapper);
        }
        List<SysResource> resources = roleVo.getResources();
        if (!ObjectUtils.isEmpty(resources)){
            updateRoleResourcesByRid(resources,entity.getId());
        }

        return true;
    }

    private void updateRoleResourcesByRid(List<SysResource> resources, Long id) {
        ArrayList<SysRoleResource> roleResources = new ArrayList<>();
        for (SysResource resource : resources) {
            SysRoleResource sysRoleResource = new SysRoleResource();
            sysRoleResource.setRoleId(id);
            sysRoleResource.setResourceId(resource.getId());
            roleResources.add(sysRoleResource);
        }
        QueryWrapper<SysRoleResource> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id",id);
        iSysRoleResourceService.remove(queryWrapper);

        iSysRoleResourceService.saveBatch(roleResources);
    }

    private void updateRoleOfficesByRid(List<SysOffice> offices, Long id) {

        ArrayList<SysRoleOffice> roleOffices = new ArrayList<>();
        for (SysOffice office : offices) {
            SysRoleOffice sysRoleOffice = new SysRoleOffice();
            sysRoleOffice.setRoleId(id);
            sysRoleOffice.setOfficeId(office.getId());
            roleOffices.add(sysRoleOffice);
        }
        QueryWrapper<SysRoleOffice> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("role_id",id);
        iSysRoleOfficeService.remove(queryWrapper);

        iSysRoleOfficeService.saveBatch(roleOffices);

    }

    @Override
    @Transactional
    public boolean save(SysRole entity) {
        RoleVo roleVo = (RoleVo) entity;
        super.save(entity);
        List<SysResource> resources = roleVo.getResources();
        if (!ObjectUtils.isEmpty(resources)){
            updateRoleResourcesByRid(resources,entity.getId());
        }
        List<SysOffice> offices = roleVo.getOffices();
        if (!ObjectUtils.isEmpty(offices)&&offices.size()!=0){
            updateRoleOfficesByRid(offices,entity.getId());
        }


        return true;
    }
}
