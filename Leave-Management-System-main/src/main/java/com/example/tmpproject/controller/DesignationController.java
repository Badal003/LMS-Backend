package com.example.tmpproject.controller;


import com.example.tmpproject.Model.DesignationModel;
import com.example.tmpproject.Model.PaginationModel;
import com.example.tmpproject.Utill.ResponseModel;
import com.example.tmpproject.service.DesignationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/designation")
@RequiredArgsConstructor
public class DesignationController {
    @Autowired
    private DesignationService designationService;

    @PostMapping("")
    public ResponseModel createUpdateDesignation(@RequestBody DesignationModel designationModel) {
        return designationService.createUpdateDesignation(designationModel);
    }

    @PutMapping("/{designationID}")
    public ResponseModel updateDesignationStatus(@PathVariable("designationID") String designationID) {
        return designationService.updateDesignationStatus(designationID);
    }

    @GetMapping("/{designationID}")
    public ResponseModel getDesignation(@PathVariable("designationID") String designationID) {
        return designationService.getDesignation(designationID);
    }

    @PostMapping("/list")
    public ResponseModel getDesignations(@RequestBody PaginationModel paginationModel) {
        return designationService.getDesignations(paginationModel);
    }
}
