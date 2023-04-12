package com.example.tmpproject.controller;

import com.example.tmpproject.Model.DepartmentModel;
import com.example.tmpproject.Model.PaginationModel;
import com.example.tmpproject.Utill.ResponseModel;
import com.example.tmpproject.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
@RequestMapping("/department")
@RequiredArgsConstructor
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    @PostMapping("")
    public ResponseModel createUpdateDepartment(@RequestBody DepartmentModel departmentModel) {
        return departmentService.createUpdateDepartment(departmentModel);
    }

    @PutMapping("/{departmentID}")
    public ResponseModel updateDepartmentStatus(@PathVariable("departmentID") String departmentID) {
        return departmentService.updateDepartmentStatus(departmentID);
    }

    @GetMapping("/{departmentID}")
    public ResponseModel getDepartment(@PathVariable("departmentID") String departmentID) {
        return departmentService.getDepartment(departmentID);
    }

    @PostMapping("/list")
    public ResponseModel getDepartments(@RequestBody PaginationModel paginationModel) {
        return departmentService.getDepartments(paginationModel);
    }
}
