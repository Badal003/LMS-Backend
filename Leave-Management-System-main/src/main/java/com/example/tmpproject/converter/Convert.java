package com.example.tmpproject.converter;

import com.example.tmpproject.Models.LeaveModel;
import com.example.tmpproject.controllers.CurrentDate;
import com.example.tmpproject.oldentity.LeaveApply;
import com.example.tmpproject.services.*;


public class Convert {


    private static LeaveService leaveService;

    private static EmployeeService employeeService;

    private static LeaveTypeService leaveTypeService;

    private static DepartmentService departmentService;


    private static DesignationService designationService;

    public static LeaveApply convertToLeaveApply(LeaveModel leaveModel) {
        LeaveApply leaveApply=new LeaveApply();
        leaveApply.setApplydate(CurrentDate.findCurrentDate());
        leaveApply.setFromDate(leaveModel.getFromDate());
        leaveApply.setToDate(leaveModel.getToDate());
        leaveApply.setStatus(0);
        leaveApply.setEmployee(employeeService.findEmployee(leaveModel.getEmployeeId()));
        leaveApply.setLeaveType(leaveTypeService.findLeaveType(leaveModel.getLeavetypeId()));
        leaveApply.setManager(employeeService.findEmployee(leaveModel.getManagerId()));
        leaveApply.setRemark(leaveApply.getRemark());
        return leaveApply;
    }
}
