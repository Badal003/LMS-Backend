package com.example.tmpproject.service;

import com.example.tmpproject.Model.EmployeePaginationModel;
import com.example.tmpproject.Model.LeaveApplyModel;
import com.example.tmpproject.Model.PaginationModel;
import com.example.tmpproject.Model.RemarkLeaveModel;
import com.example.tmpproject.Utill.ResponseModel;

public interface LeaveService {
    ResponseModel applyOrUpdateLeave(LeaveApplyModel leaveApplyModel);
    ResponseModel reviewLeave(RemarkLeaveModel remarkLeaveModel);
    ResponseModel updateLeaveStatus(String leaveApplyID);
    ResponseModel getLeaveDetails(String leaveApplyID);
    ResponseModel getAllLeaveDetails(EmployeePaginationModel employeePaginationModel);
}
