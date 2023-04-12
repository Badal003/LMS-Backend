package com.example.tmpproject.service.impl;

import com.example.tmpproject.Model.*;
import com.example.tmpproject.Utill.CommonUtill;
import com.example.tmpproject.Utill.Message;
import com.example.tmpproject.Utill.ResponseModel;
import com.example.tmpproject.entity.Department;
import com.example.tmpproject.entity.Designation;
import com.example.tmpproject.entity.Employee;
import com.example.tmpproject.enums.DeleteStatus;
import com.example.tmpproject.enums.Status;
import com.example.tmpproject.repository.DepartmentRepository;
import com.example.tmpproject.repository.DesignationRepository;
import com.example.tmpproject.repository.EmployeeRepository;
import com.example.tmpproject.service.EmployeeService;
import org.aspectj.weaver.patterns.HasMemberTypePatternForPerThisMatching;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private static final Logger logger = LoggerFactory.getLogger(DepartmentServiceImpl.class);
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private DesignationRepository designationRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    private PasswordEncoder bcryptEncoder;

    @Override
    public ResponseModel createUpdateEmployee(EmployeeModel employeeModel) {
        try {
            Employee employee = new Employee();
            boolean isUpdated = false;
            if (!employeeModel.getEmployeeID().trim().isEmpty() && employeeModel.getEmployeeID() != null) {
                employee = employeeRepository.getById(employeeModel.getEmployeeID());
                isUpdated = true;
                if (employee == null) {
                    CommonUtill.create(Message.EMPLOYEE_NOT_FOUND, null, HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value());
                }
            }
            employeeModel.setEmployeeID(employee.getEmployeeID());
            BeanUtils.copyProperties(employeeModel, employee);
            employee.setDesignationID(setEmployeeDesignationFromEmployeeModel(employeeModel.getDesignationID()).get());
            employee.setDepartmentID(setEmployeeDepartmentFromEmployeeModel(employeeModel.getDepartmentID()).get());
            employee.setDateOfJoin(CommonUtill.convertStringToCalendarDateAndTime(employeeModel.getDateOfJoin()));
            employee.setDateOfBirth(CommonUtill.convertStringToCalendarDateAndTime(employeeModel.getDateOfBirth()));
            if (!isUpdated) {
                String pass = CommonUtill.generateRandomPassword();
                employee.setPassword(bcryptEncoder.encode(pass));
            }
            employee = employeeRepository.save(employee);
            return CommonUtill.create(isUpdated ? Message.EMPLOYEE_UPDATED_SUCCESSFULLY : Message.EMPLOYEE_CREATED_SUCCESSFULLY, employee.getEmployeeID(), HttpStatus.OK, HttpStatus.OK.value());
        } catch (Exception e) {
            logger.error("EmployeeServiceImpl createUpdateEmployee ======= {}", e.getMessage());
            return CommonUtill.create(Message.SOMETHING_WRONG, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Override
    public ResponseModel updateEmployeeStatus(String employeeID) {
        try {
            Optional<Employee> employee = employeeRepository.findById(employeeID);
            if (employee.isPresent()) {
                employee.get().setStatus(Status.DEACTIVATE);
                employee.get().setMarkDelete(DeleteStatus.Y);
                employeeRepository.save(employee.get());
                return CommonUtill.create(Message.EMPLOYEE_STATUS_UPDATED_SUCCESSFULLY, employee.get().getEmployeeID(), HttpStatus.OK, HttpStatus.OK.value());
            } else {
                return CommonUtill.create(Message.PARAMETER_NOT_SUPPLIED, null, HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value());
            }
        } catch (Exception e) {
            logger.error("EmployeeServiceImpl updateEmployeeStatus ======= {}", e.getMessage());
            return CommonUtill.create(Message.SOMETHING_WRONG, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Override
    public ResponseModel getEmployee(String employeeID) {
        try {
            Optional<Employee> employee = employeeRepository.findById(employeeID);
            if (employee.isPresent()) {
                EmployeeResponseModel employeeResponseModel = setEmployeeResponseModelFromEmployee(employee.get());
                return CommonUtill.create(Message.EMPLOYEE_FOUND, employeeResponseModel, HttpStatus.OK, HttpStatus.OK.value());
            } else {
                return CommonUtill.create(Message.PARAMETER_NOT_SUPPLIED, null, HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value());
            }
        } catch (Exception e) {
            logger.error("EmployeeServiceImpl getEmployee ======= {}", e.getMessage());
            return CommonUtill.create(Message.SOMETHING_WRONG, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Override
    public ResponseModel getEmployees(EmployeePaginationModel employeePaginationModel) {
        try {
            Pageable page;
            if (employeePaginationModel.getSortOrder().equals("asc")) {
                page = PageRequest.of(employeePaginationModel.getPage(), employeePaginationModel.getLimit(), Sort.by("createdAt").ascending());
            } else {
                page = PageRequest.of(employeePaginationModel.getPage(), employeePaginationModel.getLimit(), Sort.by("createdAt").descending());
            }
            Page<Employee> employeeList = employeeRepository.findAllEmployee(employeePaginationModel.getDepartmentID(), employeePaginationModel.getSearchKeyword(), page);
            if (!employeeList.isEmpty()) {
                List<EmployeeListResponseModel> employeeListResponseModels = setEmployeeListResponseModelFromEmployees(employeeList);
                FinalListingModel finalListingModel = new FinalListingModel();
                finalListingModel.setPage(employeePaginationModel.getPage());
                finalListingModel.setLimit(employeePaginationModel.getLimit());
                finalListingModel.setTotalData(employeeList.getTotalElements());
                finalListingModel.setData(employeeListResponseModels);
                return CommonUtill.create(Message.EMPLOYEE_GET_SUCCESSFULLY, finalListingModel, HttpStatus.OK, HttpStatus.OK.value());
            } else {
                return CommonUtill.create(Message.EMPLOYEE_NOT_FOUND, null, HttpStatus.OK, HttpStatus.OK.value());
            }
        } catch (Exception e) {
            logger.error("EmployeeServiceImpl getEmployees ======= {}", e.getMessage());
            return CommonUtill.create(Message.SOMETHING_WRONG, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Override
    public ResponseModel updateEmployeeRole(UserRoleModel userRoleModel) {
        try {
            Optional<Employee> employee = employeeRepository.findById(userRoleModel.getEmployeeID());
            if (employee.isPresent()) {
                employee.get().setUserRole(userRoleModel.getUserRole());
                employeeRepository.save(employee.get());
                return CommonUtill.create(Message.EMPLOYEE_ROLE_UPDATED_SUCCESSFULLY, employee.get().getEmployeeID(), HttpStatus.OK, HttpStatus.OK.value());
            } else {
                return CommonUtill.create(Message.PARAMETER_NOT_SUPPLIED, null, HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value());
            }
        } catch (Exception e) {
            logger.error("EmployeeServiceImpl updateEmployeeRole ======= {}", e.getMessage());
            return CommonUtill.create(Message.SOMETHING_WRONG, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    private Optional<Designation> setEmployeeDesignationFromEmployeeModel(String designationID) {
        Optional<Designation> designation = designationRepository.findById(designationID);
        return designation.isPresent() ? designation : null;
    }

    private Optional<Department> setEmployeeDepartmentFromEmployeeModel(String departmentID) {
        Optional<Department> department = departmentRepository.findById(departmentID);
        return department.isPresent() ? department : null;
    }

    private EmployeeResponseModel setEmployeeResponseModelFromEmployee(Employee employee) {
        EmployeeResponseModel employeeResponseModel = new EmployeeResponseModel();
        employeeResponseModel.setEmployeeID(employee.getEmployeeID());
        employeeResponseModel.setFirstName(employee.getFirstName());
        employeeResponseModel.setMiddleName(employee.getMiddleName());
        employeeResponseModel.setLastName(employee.getLastName());
        employeeResponseModel.setGender(employeeResponseModel.getGender());
        employeeResponseModel.setEmailId(employee.getEmailId());
        employeeResponseModel.setMobileNumber(employee.getMobileNumber());
        employeeResponseModel.setDateOfBirth(employee.getDateOfBirth());
        employeeResponseModel.setDateOfJoin(employee.getDateOfJoin());
        employeeResponseModel.setDepartmentName(employee.getDepartmentID().getShortName());
        employeeResponseModel.setDesignationName(employee.getDesignationID().getName());
        employeeResponseModel.setUserRole(employee.getUserRole());
        return employeeResponseModel;
    }

    private List<EmployeeListResponseModel> setEmployeeListResponseModelFromEmployees(Page<Employee> employees) {
        List<EmployeeListResponseModel> employeeListResponseModels = new ArrayList<>();
        for (Employee employee : employees) {
            EmployeeListResponseModel employeeListResponseModel = new EmployeeListResponseModel();
            employeeListResponseModel.setEmployeeID(employee.getEmployeeID());
            employeeListResponseModel.setFirstName(employee.getFirstName());
            employeeListResponseModel.setMiddleName(employee.getMiddleName());
            employeeListResponseModel.setLastName(employee.getLastName());
            employeeListResponseModel.setDateOfBirth(employee.getDateOfBirth());
            employeeListResponseModel.setDateOfJoin(employee.getDateOfJoin());
            employeeListResponseModel.setUserRole(employee.getUserRole());
            employeeListResponseModels.add(employeeListResponseModel);
        }
        return employeeListResponseModels;
    }

}
