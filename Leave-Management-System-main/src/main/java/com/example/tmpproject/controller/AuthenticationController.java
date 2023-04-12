package com.example.tmpproject.controller;

import com.example.tmpproject.Model.LoginModel;
import com.example.tmpproject.Utill.ResponseModel;
import com.example.tmpproject.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@RestController
@CrossOrigin
@RequestMapping("/authenticate")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseModel login(@RequestBody LoginModel loginModel) {
        return authenticationService.login(loginModel);
    }

    @PostMapping("/forget")
    public ResponseModel forgetPassword(@RequestBody LoginModel loginModel) {
        return authenticationService.forgetPassword(loginModel);
    }

    @PostMapping("/update")
    public ResponseModel updatePassword(@RequestBody LoginModel loginModel) {
        return authenticationService.updatePassword(loginModel);
    }
}
