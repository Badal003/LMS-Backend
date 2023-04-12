package com.example.tmpproject.controllers;

import com.example.tmpproject.Models.EmployeeModel;
import com.example.tmpproject.oldentity.Department;
import com.example.tmpproject.oldentity.Designation;
import com.example.tmpproject.oldentity.Employee;
import com.example.tmpproject.oldentity.UserRole;
import com.example.tmpproject.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

@RestController
public class EmployeeController
{
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private DesignationService designationService;
    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private EmailService emailService;




    @PostMapping("/addemployee")
    @CrossOrigin(origins = "http://localhost:4200")
    public Employee AddEmployee(@RequestBody EmployeeModel employeemodel) throws MessagingException, IOException {
        Employee employee=new Employee();
        employee.setFirstName(employeemodel.getFirstName());
        employee.setMiddleName(employeemodel.getMiddleName());
        employee.setLastName(employeemodel.getLastName());
        employee.setGender(employeemodel.getGender());
        employee.setEmailId(employeemodel.getEmailId());
        String password=GeneratePassword.generateRandomPassword();
        String subject="Welcome to LMS";
        String text="Hi  " +employee.getFirstName()+
                "your password "+password;
        emailService.sendMail(employeemodel.getEmailId(),subject,text);
        employee.setPassword(password);
        employee.setPassword(password);
        employee.setMobileNumber(employeemodel.getMobileNumber());
        employee.setDateOfBirth(employeemodel.getDateOfBirth());
        employee.setDateOfJoin(employeemodel.getDateOfJoin());
        employee.setDepartment(departmentService.findByDepartment(employeemodel.getDepartmentId()));
        employee.setDesignation(designationService.findDesination(employeemodel.getDesignationId()));
        employee.setUserRole(userRoleService.findUserRole(2));
        //employee.setPassword(GeneratePassword.generateRandomPassword());
        return employeeService.saveEmployee(employee);
    }
    @PostMapping("/findemployee")
    @CrossOrigin(origins = "http://localhost:4200")
    public EmployeeModel FindEmployee(@RequestBody EmployeeModel employeemodel)
    {
        EmployeeModel employeeModel1 =new EmployeeModel();
        Employee employee=employeeService.findEmployee(employeemodel.getId());
        employeeModel1.setId(employee.getEmployeeId());
        employeeModel1.setFirstName(employee.getFirstName());
        employeeModel1.setMiddleName(employee.getMiddleName());
        employeeModel1.setLastName(employee.getLastName());
        employeeModel1.setGender(employee.getGender());
        employeeModel1.setEmailId(employee.getEmailId());
        employeeModel1.setPassword(employee.getPassword());
        employeeModel1.setMobileNumber(employee.getMobileNumber());
        employeeModel1.setDateOfBirth(employee.getDateOfBirth());
        employeeModel1.setDateOfJoin(employee.getDateOfJoin());
        Designation designation=employee.getDesignation();
        Department department=employee.getDepartment();
        UserRole userRole=employee.getUserRole();
        employeeModel1.setDepartmentId(department.getDepartmentId());
        employeeModel1.setDesignationId(designation.getDesignationId());
        employeeModel1.setUserroleId(userRole.getUserroleId());
        return employeeModel1;
    }
    @PostMapping("/findallemployee")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<Employee> FindAllEmployee()
    {
        return employeeService.findAllEmployee();
    }

    @PostMapping("/updateemployee")
    @CrossOrigin(origins = "http://localhost:4200")
    public Employee UpdateEmployee(@RequestBody EmployeeModel employeemodel)
    {
        Employee employee=employeeService.findEmployee(employeemodel.getId());
        employee.setFirstName(employeemodel.getFirstName());
        employee.setMiddleName(employeemodel.getMiddleName());
        employee.setLastName(employeemodel.getLastName());
        employee.setGender(employeemodel.getGender());
        employee.setEmailId(employeemodel.getEmailId());
        employee.setMobileNumber(employeemodel.getMobileNumber());
        employee.setDateOfBirth(employeemodel.getDateOfBirth());
        employee.setDateOfJoin(employeemodel.getDateOfJoin());
        employee.setDepartment(departmentService.findByDepartment(employeemodel.getDepartmentId()));
        employee.setDesignation(designationService.findDesination(employeemodel.getDesignationId()));
        return employeeService.saveEmployee(employee);
    }
    @PostMapping("/deleteemployee")
    @CrossOrigin(origins = "http://localhost:4200")
    public Employee DeleteEmployee(@RequestBody Employee employee)
    {
        Employee employee1=new Employee();
        employee1=employeeService.findEmployee(employee.getEmployeeId());
        employee1.setDepartment(null);
        employee1.setDesignation(null);
        employee1.setUserRole(null);
        employee1=employeeService.saveEmployee(employee1);
        return employeeService.deleteEmployee(employee1.getEmployeeId());
    }

    @PostMapping("/findemployeebydepartment")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<Employee> findEmployeeByDepartment(@RequestBody EmployeeModel employeemodel)
    {
        Department department=departmentService.findByDepartment(employeemodel.getDepartmentId());
        return employeeService.findEmployeeByDepartment(department);
    }

    @PostMapping("/updateEmployeeByUserrole")
    @CrossOrigin(origins = "http://localhost:4200")
    public Employee updateEmployeeByUserRole(@RequestBody EmployeeModel employeemodel)
    {

        Employee employee=employeeService.findEmployee(employeemodel.getId());
        UserRole userRole=userRoleService.findUserRole(employeemodel.getUserroleId());
        employee.setUserRole(userRole);
        return employeeService.saveEmployee(employee);
    }


    @PostMapping("/login")
    @CrossOrigin(origins = "http://localhost:4200")
    public EmployeeModel employeeLogin(@RequestBody EmployeeModel employeemodel)
    {
        EmployeeModel employeeModel1 =new EmployeeModel();
        Employee employee=employeeService.findEmployeeByEmailandPassword(employeemodel.getEmailId(), employeemodel.getPassword());
        employeeModel1.setId(employee.getEmployeeId());
        employeeModel1.setFirstName(employee.getFirstName());
        employeeModel1.setMiddleName(employee.getMiddleName());
        employeeModel1.setLastName(employee.getLastName());
        employeeModel1.setGender(employee.getGender());
        employeeModel1.setEmailId(employee.getEmailId());
        employeeModel1.setPassword(GeneratePassword.generateRandomPassword());
        employeeModel1.setMobileNumber(employee.getMobileNumber());
        employeeModel1.setDateOfBirth(employee.getDateOfBirth());
        employeeModel1.setDateOfJoin(employee.getDateOfJoin());
        Designation designation=employee.getDesignation();
        Department department=employee.getDepartment();
        UserRole userRole=employee.getUserRole();
        employeeModel1.setDepartmentId(department.getDepartmentId());
//        employeemodule1.setDesignationId(designation.getDesignationId());
        employeeModel1.setUserroleId(userRole.getUserroleId());
        return employeeModel1;
    }
    @PostMapping("/updatepassword")
    @CrossOrigin(origins = "http://localhost:4200")
    public Employee updatePassword(@RequestBody EmployeeModel employeemodel)
    {
        Employee employee=employeeService.findEmployee(employeemodel.getId());
        employee.setPassword(employeemodel.getPassword());
        return employeeService.saveEmployee(employee);
    }

        @PostMapping("/forgetpassword")
    @CrossOrigin(origins = "http://localhost:4200")
    public Employee forgetPassword(@RequestBody EmployeeModel employeemodel) throws MessagingException {
        Employee employee=employeeService.findEmployeeByEmail(employeemodel.getEmailId());
        String password=GeneratePassword.generateRandomPassword();
        String subject="New Generate Password from LMS";
        String text="Hi "+employee.getFirstName()
                +"\n      Welcome to LMS."
                +"\n To make reservations, you will need a login for Schedule Master. Please use the links below to  your password and then go to the login page using the following account information:\n username: "+employee.getEmailId() +"\n" +
                "Password:"+ password+"\n   Thanks,  \nbypt Leave Management System";
        emailService.sendMail(employeemodel.getEmailId(),subject,text);
        employee.setPassword(password);
        return employeeService.saveEmployee(employee);
    }
}
