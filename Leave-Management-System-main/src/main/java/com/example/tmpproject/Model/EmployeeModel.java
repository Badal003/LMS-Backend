package com.example.tmpproject.Model;

import com.example.tmpproject.enums.UserRole;
import lombok.Data;


@Data
public class EmployeeModel {
    private String employeeID;
    private String firstName;
    private String middleName;
    private String lastName;
    private String gender;
    private String emailId;
    private String mobileNumber;
    private String password;
    private String dateOfBirth;
    private String dateOfJoin;
    private String departmentID;
    private String designationID;
    private UserRole userRole;

}


