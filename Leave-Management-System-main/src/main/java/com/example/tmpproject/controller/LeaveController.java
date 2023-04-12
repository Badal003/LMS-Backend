package com.example.tmpproject.controller;

import com.example.tmpproject.Model.EmployeePaginationModel;
import com.example.tmpproject.Model.LeaveApplyModel;
import com.example.tmpproject.Model.RemarkLeaveModel;
import com.example.tmpproject.Utill.ResponseModel;
import com.example.tmpproject.service.LeaveService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/leave")
@RequiredArgsConstructor
public class LeaveController {
    @Autowired
    private LeaveService leaveService;

    @PostMapping("/apply")
    public ResponseModel applyOrUpdateLeave(@RequestBody LeaveApplyModel leaveApplyModel) {
        return leaveService.applyOrUpdateLeave(leaveApplyModel);
    }

    @PostMapping("/review")
    public ResponseModel reviewLeave(@RequestBody RemarkLeaveModel remarkLeaveModel) {
        return leaveService.reviewLeave(remarkLeaveModel);
    }

    @PutMapping("/{leaveApplyID}")
    public ResponseModel updateLeaveStatus(@PathVariable("leaveApplyID") String leaveApplyID) {
        return leaveService.updateLeaveStatus(leaveApplyID);
    }

    @GetMapping("/{leaveApplyID}")
    public ResponseModel getLeaveDetails(@PathVariable("leaveApplyID") String leaveApplyID) {
        return leaveService.getLeaveDetails(leaveApplyID);
    }

    @PostMapping("/list")
    public ResponseModel getAllLeaveDetails(@RequestBody EmployeePaginationModel employeePaginationModel) {
        return leaveService.getAllLeaveDetails(employeePaginationModel);
    }
}
