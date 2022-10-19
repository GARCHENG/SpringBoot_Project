package com.gec.hawaste.vo;

import com.gec.hawaste.entity.Examine;
import lombok.Data;

@Data
public class ExamineVo extends Examine {
    private String officeName;
    private String userName;
}
