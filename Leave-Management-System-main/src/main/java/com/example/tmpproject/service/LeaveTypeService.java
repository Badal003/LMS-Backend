package com.example.tmpproject.service;

import com.example.tmpproject.Model.LeaveTypeModel;
import com.example.tmpproject.Model.PaginationModel;
import com.example.tmpproject.Utill.ResponseModel;

public interface LeaveTypeService {
    ResponseModel createUpdateLeaveType(LeaveTypeModel leaveTypeModel);

    ResponseModel updateLeaveTypeStatus(String leaveTypeID);

    ResponseModel getLeaveType(String leaveTypeID);

    ResponseModel getLeaveTypes(PaginationModel paginationModel);
}
