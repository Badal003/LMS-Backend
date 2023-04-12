package com.example.tmpproject.service;

import com.example.tmpproject.Model.EmployeeModel;
import com.example.tmpproject.Model.EmployeePaginationModel;
import com.example.tmpproject.Model.PaginationModel;
import com.example.tmpproject.Model.UserRoleModel;
import com.example.tmpproject.Utill.ResponseModel;

public interface EmployeeService {

    ResponseModel createUpdateEmployee(EmployeeModel employeeModel);
    ResponseModel updateEmployeeStatus(String employeeID);
    ResponseModel getEmployee(String employeeID);
    ResponseModel getEmployees(EmployeePaginationModel employeePaginationModel);
    ResponseModel updateEmployeeRole(UserRoleModel userRoleModel);
}
