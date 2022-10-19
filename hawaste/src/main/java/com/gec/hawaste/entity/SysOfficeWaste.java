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
 * 机构-危废品
 * </p>
 *
 * @author GARCHENG
 * @since 2022-10-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SysOfficeWaste implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 危废品编号
     */
    private Long wasteId;

    /**
     * 机构编号
     */
    private Long officeId;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String createBy;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createDate;

    private String updateBy;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateDate;

    @TableField(fill = FieldFill.INSERT)
    @TableLogic
    private String delFlag;


}
