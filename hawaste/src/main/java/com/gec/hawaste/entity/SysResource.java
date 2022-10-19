package com.gec.hawaste.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author GARCHENG
 * @since 2022-10-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysResource implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 资源名称
     */
    private String name;

    /**
     * 是否是公共资源(0.不是 1.是)
     */
    private String common;

    /**
     * 图标
     */
    private String icon;

    /**
     * 排序号
     */
    private Integer sort;

    /**
     * 父级id
     */
    private Long parentId;

    /**
     * 类型(0.菜单 1.按钮)
     */
    private String type;

    /**
     * 链接
     */
    private String url;

    /**
     * 描述
     */
    private String description;

    /**
     * 状态(0.正常 1.禁用)
     */
    private String status;

    /**
     * 父级集合
     */
    private String parentIds;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createDate;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateDate;

    private String createBy;

    private String updateBy;

    @TableField(fill = FieldFill.INSERT)
    @TableLogic
    private String delFlag;

    private String permissionStr;


}
