package com.example.tmpproject.Model;

import com.example.tmpproject.enums.LeaveStatus;
import lombok.Data;

import java.util.Calendar;

@Data
public class LeaveApplyResponseModel {
    private String leaveApplyID;
    private Calendar fromDate;
    private Calendar toDate;
    private Calendar applyDate;
    private LeaveStatus leaveStatus;
    private String leaveTypeName;
    private String employeeName;
}
