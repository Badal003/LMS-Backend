package com.example.tmpproject.Model;

import lombok.Data;

import java.util.Calendar;

@Data
public class DepartmentResponseModel {
    private String departmentID;
    private String shortName;
    private String fullName;
    private Calendar createdAt;
}
