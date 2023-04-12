package com.example.tmpproject.Model;

import com.example.tmpproject.enums.UserRole;
import lombok.Data;

@Data
public class UserRoleModel {
    private String employeeID;
    private UserRole userRole;
}
