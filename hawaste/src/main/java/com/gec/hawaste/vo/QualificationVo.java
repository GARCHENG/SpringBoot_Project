package com.gec.hawaste.vo;

import com.gec.hawaste.entity.Qualification;
import lombok.Data;

@Data
public class QualificationVo extends Qualification {

//    u.`name` user_name,
//    cu.`name` check_user_name

    private String userName;
    private String checkUserName;
}
