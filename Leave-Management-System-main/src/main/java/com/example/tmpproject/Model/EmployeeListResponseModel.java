package com.example.tmpproject.Model;

import com.example.tmpproject.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Calendar;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeListResponseModel {
    private String employeeID;
    private String firstName;
    private String middleName;
    private String lastName;
    private Calendar dateOfBirth;
    private Calendar dateOfJoin;
    private UserRole userRole;
}
