package com.gec.hawaste.service;

import com.gec.hawaste.entity.SysResource;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author GARCHENG
 * @since 2022-10-12
 */
public interface ISysResourceService extends IService<SysResource> {

    List<SysResource> selectResourceByRid(Integer rid);

}
