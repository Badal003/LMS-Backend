package com.example.tmpproject.Model;

import com.example.tmpproject.enums.LeaveStatus;
import lombok.Data;


@Data
public class LeaveApplyModel {
    private String leaveApplyID;
    private String fromDate;
    private String toDate;
    private String applyDate;
    private LeaveStatus leaveStatus;
    private String leaveTypeID;
    private String employeeID;
}
