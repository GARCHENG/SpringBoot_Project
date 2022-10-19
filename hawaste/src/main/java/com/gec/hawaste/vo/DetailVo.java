package com.gec.hawaste.vo;

import com.gec.hawaste.entity.Detail;
import lombok.Data;

@Data
public class DetailVo extends Detail {
//    w.`code` waste_code,
//    wt.`name` waste_type_name,
//    wt.`code` waste_type_code

    private String wasteCode;
    private String wasteTypeName;
    private String wasteTypeCode;


}
