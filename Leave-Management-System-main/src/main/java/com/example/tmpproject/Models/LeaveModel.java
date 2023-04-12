package com.example.tmpproject.Models;

import lombok.Data;

import java.util.Date;

@Data
public class LeaveModel
{
    private int leaveapplyId;
    private Date fromDate;
    private Date toDate;
    private Date applydate;
    private int status;
    private Date remarkdate;
    private String remark;
    private int leavetypeId;
    private int employeeId;
    private int managerId;
}
