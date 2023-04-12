package com.example.tmpproject.controller;

import com.example.tmpproject.Model.EmployeeModel;
import com.example.tmpproject.Model.EmployeePaginationModel;
import com.example.tmpproject.Model.PaginationModel;
import com.example.tmpproject.Model.UserRoleModel;
import com.example.tmpproject.Utill.ResponseModel;
import com.example.tmpproject.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/employee")
@RequiredArgsConstructor
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping("")
    public ResponseModel createUpdateEmployee(@RequestBody EmployeeModel employeeModel) {
        return employeeService.createUpdateEmployee(employeeModel);
    }

    @PutMapping("/{employeeID}")
    public ResponseModel updateEmployeeStatus(@PathVariable("employeeID") String employeeID) {
        return employeeService.updateEmployeeStatus(employeeID);
    }

    @GetMapping("/{employeeID}")
    public ResponseModel getEmployee(@PathVariable("employeeID") String employeeID) {
        return employeeService.getEmployee(employeeID);
    }

    @PostMapping("/list")
    public ResponseModel getEmployees(@RequestBody EmployeePaginationModel employeePaginationModel) {
        return employeeService.getEmployees(employeePaginationModel);
    }

    @PostMapping("/role")
    public ResponseModel updateEmployeeRole(@RequestBody UserRoleModel userRoleModel) {
        return employeeService.updateEmployeeRole(userRoleModel);
    }
}
