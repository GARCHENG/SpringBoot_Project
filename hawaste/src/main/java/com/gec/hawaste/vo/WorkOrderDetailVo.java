package com.gec.hawaste.vo;

import lombok.Data;

import java.util.List;

@Data
public class WorkOrderDetailVo extends WorkOrderVo {


    private List<DetailVo> details;
    private List<TransferVo> transfers;

//    co.`name` create_office_name,
//    cu.phone create_user_phone,
    private String createOfficeName;
    private String createUserPhone;

    private String transportOfficeName;
    private String transportUserPhone;

    private String recipientOfficeName;
    private String recipientUserPhone;
}
