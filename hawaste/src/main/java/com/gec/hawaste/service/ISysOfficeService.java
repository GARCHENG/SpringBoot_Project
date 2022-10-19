package com.gec.hawaste.service;

import com.gec.hawaste.entity.SysOffice;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 机构表 服务类
 * </p>
 *
 * @author GARCHENG
 * @since 2022-10-12
 */
public interface ISysOfficeService extends IService<SysOffice> {

    List<SysOffice> selectOfficesByRid(Integer rid);

}
