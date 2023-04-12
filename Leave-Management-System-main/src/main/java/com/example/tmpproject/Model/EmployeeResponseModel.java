package com.example.tmpproject.Model;

import com.example.tmpproject.enums.UserRole;

import lombok.Data;
import java.util.Calendar;

@Data
public class EmployeeResponseModel {
    private String employeeID;
    private String firstName;
    private String middleName;
    private String lastName;
    private String gender;
    private String emailId;
    private String mobileNumber;
    private String password;
    private Calendar dateOfBirth;
    private Calendar dateOfJoin;
    private String departmentName;
    private String designationName;
    private UserRole userRole;
}
