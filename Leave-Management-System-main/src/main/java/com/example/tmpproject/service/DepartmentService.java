package com.example.tmpproject.service;

import com.example.tmpproject.Model.DepartmentModel;
import com.example.tmpproject.Model.PaginationModel;
import com.example.tmpproject.Utill.ResponseModel;

public interface DepartmentService {
    ResponseModel createUpdateDepartment(DepartmentModel departmentModel);

    ResponseModel updateDepartmentStatus(String departmentID);

    ResponseModel getDepartment(String departmentID);

    ResponseModel getDepartments(PaginationModel paginationModel);
}
