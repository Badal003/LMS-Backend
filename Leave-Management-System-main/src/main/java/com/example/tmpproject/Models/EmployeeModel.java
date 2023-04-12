package com.example.tmpproject.Models;
import lombok.Data;
import java.util.Date;

@Data

public class EmployeeModel
{
    private int id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String gender;
    private String emailId;
    private long mobileNumber;
    private String password;
    private Date dateOfBirth;
    private Date dateOfJoin;
    private int departmentId;
    private int designationId;
    private int userroleId;

}


