package com.gec.hawaste.vo;

import com.gec.hawaste.entity.WorkOrder;
import lombok.Data;


@Data
public class WorkOrderVo extends WorkOrder {


//    cu.`name` create_user_name,
//    tu.`name` transport_user_name,
//    ru.`name` recipient_user_name

    private String createUserName;
    private String transportUserName;
    private String recipientUserName;
    private String createOfficeName;
}
