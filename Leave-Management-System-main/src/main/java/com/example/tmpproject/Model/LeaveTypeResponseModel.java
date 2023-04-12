package com.example.tmpproject.Model;

import lombok.Data;

import java.util.Calendar;

@Data
public class LeaveTypeResponseModel {
    private String leaveTypeID;
    private String leaveName;
    private String description;
    private Calendar createdAt;
}
