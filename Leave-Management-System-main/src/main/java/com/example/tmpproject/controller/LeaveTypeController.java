package com.example.tmpproject.controller;


import com.example.tmpproject.Model.LeaveTypeModel;
import com.example.tmpproject.Model.PaginationModel;
import com.example.tmpproject.Utill.ResponseModel;
import com.example.tmpproject.service.LeaveTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/leave/type")
@RequiredArgsConstructor
public class LeaveTypeController {
    @Autowired
    private LeaveTypeService leaveTypeService;

    @PostMapping("")
    public ResponseModel createUpdateLeaveType(@RequestBody LeaveTypeModel leaveTypeModel) {
        return leaveTypeService.createUpdateLeaveType(leaveTypeModel);
    }

    @PutMapping("/{leaveTypeID}")
    public ResponseModel updateLeaveTypeStatus(@PathVariable("leaveTypeID") String leaveTypeID) {
        return leaveTypeService.updateLeaveTypeStatus(leaveTypeID);
    }

    @GetMapping("/{leaveTypeID}")
    public ResponseModel getLeaveType(@PathVariable("leaveTypeID") String leaveTypeID) {
        return leaveTypeService.getLeaveType(leaveTypeID);
    }

    @PostMapping("/list")
    public ResponseModel getLeaveTypes(@RequestBody PaginationModel paginationModel) {
        return leaveTypeService.getLeaveTypes(paginationModel);
    }
}
