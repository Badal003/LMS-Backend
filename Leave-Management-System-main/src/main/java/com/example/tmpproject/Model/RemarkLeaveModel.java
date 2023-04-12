package com.example.tmpproject.Model;

import com.example.tmpproject.enums.LeaveStatus;
import lombok.Data;

@Data
public class RemarkLeaveModel {
    private String leaveApplyID;
    private String remarkDate;
    private LeaveStatus leaveStatus;
    private String remark;
    private String employeeID;
    private String managerID;
}
