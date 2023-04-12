package com.example.tmpproject.service.impl;

import com.example.tmpproject.Model.LoginModel;
import com.example.tmpproject.Utill.CommonUtill;
import com.example.tmpproject.Utill.Message;
import com.example.tmpproject.Utill.ResponseModel;
import com.example.tmpproject.entity.Employee;
import com.example.tmpproject.repository.EmployeeRepository;
import com.example.tmpproject.service.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;
    private PasswordEncoder bcryptEncoder;

    @Override
    public ResponseModel login(LoginModel loginModel) {
        try {
            if (!loginModel.getEmailId().trim().isEmpty() && !loginModel.getPassword().trim().isEmpty()) {
                String pass = bcryptEncoder.encode(loginModel.getPassword());
                Optional<Employee> employee = employeeRepository.findByEmailIdAndPassword(loginModel.getEmailId(), pass);
                if (employee.isPresent()) {
                    return CommonUtill.create(Message.LOGIN_SUCCESS, true, HttpStatus.OK, HttpStatus.OK.value());
                } else {
                    return CommonUtill.create(Message.LOGIN_ERROR, false, HttpStatus.OK, HttpStatus.OK.value());
                }
            } else {
                return CommonUtill.create(Message.PARAMETER_NOT_SUPPLIED, null, HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value());
            }
        } catch (Exception e) {
            logger.error("AuthenticationServiceImpl login ======= {}", e.getMessage());
            return CommonUtill.create(Message.SOMETHING_WRONG, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Override
    public ResponseModel forgetPassword(LoginModel loginModel) {
        try {
            Optional<Employee> employee = employeeRepository.findByEmailId(loginModel.getEmailId());
            if (employee.isPresent()) {
                String pass = CommonUtill.generateRandomPassword();
                String subject = "New Generate Password from LMS";
                String text = "Hi " + employee.get().getFirstName()
                        + "\n      Welcome to LMS."
                        + "\n To make reservations, you will need a login for Schedule Master. Please use the links below to  your password and then go to the login page using the following account information:\n username: " + employee.get().getEmailId() + "\n" +
                        "Password:" + pass + "\n   Thanks,  \nbypt Leave Management System";
                CommonUtill.sendMail(employee.get().getEmailId(), subject, text);
                employee.get().setPassword(bcryptEncoder.encode(pass));
                employeeRepository.save(employee.get());
                return CommonUtill.create(Message.PSWD_SEND_SUCCESSFULLY, employee.get().getEmployeeID(), HttpStatus.OK, HttpStatus.OK.value());
            } else {
                return CommonUtill.create(Message.PARAMETER_NOT_SUPPLIED, null, HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value());
            }
        } catch (Exception e) {
            logger.error("AuthenticationServiceImpl forgetPassword ======= {}", e.getMessage());
            return CommonUtill.create(Message.SOMETHING_WRONG, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Override
    public ResponseModel updatePassword(LoginModel loginModel) {
        try {
            Optional<Employee> employee = employeeRepository.findByEmailId(loginModel.getEmailId());
            if (employee.isPresent()) {
                employee.get().setPassword(bcryptEncoder.encode(loginModel.getPassword()));
                employeeRepository.save(employee.get());
                return CommonUtill.create(Message.PSWD_UPT_SUCCESS, employee.get().getEmployeeID(), HttpStatus.OK, HttpStatus.OK.value());
            } else {
                return CommonUtill.create(Message.PARAMETER_NOT_SUPPLIED, null, HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value());
            }
        } catch (Exception e) {
            logger.error("AuthenticationServiceImpl updatePassword ======= {}", e.getMessage());
            return CommonUtill.create(Message.SOMETHING_WRONG, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }
}
