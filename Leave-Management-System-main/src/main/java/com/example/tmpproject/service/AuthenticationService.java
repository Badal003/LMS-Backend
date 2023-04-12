package com.example.tmpproject.service;

import com.example.tmpproject.Model.LoginModel;
import com.example.tmpproject.Utill.ResponseModel;

public interface AuthenticationService {
    ResponseModel login(LoginModel loginModel);
    ResponseModel forgetPassword(LoginModel loginModel);
    ResponseModel updatePassword(LoginModel loginModel);
}
