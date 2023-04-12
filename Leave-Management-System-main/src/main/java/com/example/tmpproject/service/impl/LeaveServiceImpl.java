package com.example.tmpproject.service.impl;

import com.example.tmpproject.Model.*;
import com.example.tmpproject.Utill.CommonUtill;
import com.example.tmpproject.Utill.Message;
import com.example.tmpproject.Utill.ResponseModel;
import com.example.tmpproject.entity.Employee;
import com.example.tmpproject.entity.LeaveApply;
import com.example.tmpproject.entity.LeaveType;
import com.example.tmpproject.enums.DeleteStatus;
import com.example.tmpproject.enums.Status;
import com.example.tmpproject.repository.EmployeeRepository;
import com.example.tmpproject.repository.LeaveRepository;
import com.example.tmpproject.repository.LeaveTypeRepository;
import com.example.tmpproject.service.LeaveService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class LeaveServiceImpl implements LeaveService {
    private static final Logger logger = LoggerFactory.getLogger(LeaveServiceImpl.class);
    @Autowired
    private LeaveRepository leaveRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private LeaveTypeRepository leaveTypeRepository;

    @Override
    public ResponseModel applyOrUpdateLeave(LeaveApplyModel leaveApplyModel) {
        try {
            LeaveApply leaveApply = new LeaveApply();
            boolean isUpdated = false;
            if (!leaveApplyModel.getLeaveApplyID().trim().isEmpty() && leaveApplyModel.getLeaveApplyID() != null) {
                leaveApply = leaveRepository.getById(leaveApplyModel.getLeaveApplyID());
                isUpdated = true;
                if (leaveApply == null) {
                    CommonUtill.create(Message.LEAVE_APPLY_NOT_FOUND, null, HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value());
                }
            }
            leaveApply.setFromDate(CommonUtill.convertStringToCalendarDateAndTime(leaveApplyModel.getFromDate()));
            leaveApply.setToDate(CommonUtill.convertStringToCalendarDateAndTime(leaveApplyModel.getToDate()));
            leaveApply.setApplyDate(Calendar.getInstance(TimeZone.getTimeZone("GMT")));
            leaveApply.setLeaveStatus(leaveApplyModel.getLeaveStatus());
            leaveApply.setEmployeeID(getEmployeeFromEmployeeID(leaveApplyModel.getEmployeeID()));
            leaveApply.setLeaveTypeID(getLeaveTypeFromLeaveTypeID(leaveApplyModel.getLeaveTypeID()));
            leaveApply = leaveRepository.save(leaveApply);
            return CommonUtill.create(isUpdated ? Message.LEAVE_APPLY_UPDATED_SUCCESSFULLY : Message.LEAVE_APPLY_CREATED_SUCCESSFULLY, leaveApply.getLeaveApplyID(), HttpStatus.OK, HttpStatus.OK.value());
        } catch (Exception e) {
            logger.error("LeaveServiceImpl updateEmployeeRole ======= {}", e.getMessage());
            return CommonUtill.create(Message.SOMETHING_WRONG, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Override
    public ResponseModel reviewLeave(RemarkLeaveModel remarkLeaveModel) {
        try {
            Optional<LeaveApply> leaveApply = leaveRepository.findById(remarkLeaveModel.getLeaveApplyID());
            if (leaveApply.isPresent()) {
                leaveApply.get().setRemark(remarkLeaveModel.getRemark());
                leaveApply.get().setRemarkDate(Calendar.getInstance(TimeZone.getTimeZone("GMT")));
                leaveApply.get().setLeaveStatus(remarkLeaveModel.getLeaveStatus());
                leaveApply.get().setManagerID(getEmployeeFromEmployeeID(remarkLeaveModel.getManagerID()));
                leaveRepository.save(leaveApply.get());
                return CommonUtill.create(Message.LEAVE_APPLY_UPDATED_SUCCESSFULLY, leaveApply.get().getLeaveApplyID(), HttpStatus.OK, HttpStatus.OK.value());
            } else {
                return CommonUtill.create(Message.PARAMETER_NOT_SUPPLIED, null, HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value());
            }
        } catch (Exception e) {
            logger.error("LeaveServiceImpl ReviewLeave ======= {}", e.getMessage());
            return CommonUtill.create(Message.SOMETHING_WRONG, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Override
    public ResponseModel updateLeaveStatus(String leaveApplyID) {
        try {
            Optional<LeaveApply> leaveApply = leaveRepository.findById(leaveApplyID);
            if (leaveApply.isPresent()) {
                leaveApply.get().setStatus(Status.DEACTIVATE);
                leaveApply.get().setMarkDelete(DeleteStatus.Y);
                leaveRepository.save(leaveApply.get());
                return CommonUtill.create(Message.LEAVE_APPLY_STATUS_UPDATED_SUCCESSFULLY, leaveApply.get().getLeaveApplyID(), HttpStatus.OK, HttpStatus.OK.value());
            } else {
                return CommonUtill.create(Message.PARAMETER_NOT_SUPPLIED, null, HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value());
            }
        } catch (Exception e) {
            logger.error("LeaveServiceImpl updateLeaveStatus ======= {}", e.getMessage());
            return CommonUtill.create(Message.SOMETHING_WRONG, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Override
    public ResponseModel getLeaveDetails(String leaveApplyID) {
        try {
            Optional<LeaveApply> leaveApply = leaveRepository.findById(leaveApplyID);
            if (leaveApply.isPresent()) {
                LeaveApplyResponseModel leaveApplyResponseModel = setLeaveApplyResponseModelFromLeaveApply(leaveApply.get());
                return CommonUtill.create(Message.LEAVE_APPLY_FOUND, leaveApplyResponseModel, HttpStatus.OK, HttpStatus.OK.value());
            } else {
                return CommonUtill.create(Message.PARAMETER_NOT_SUPPLIED, null, HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value());
            }
        } catch (Exception e) {
            logger.error("LeaveServiceImpl getLeaveDetails ======= {}", e.getMessage());
            return CommonUtill.create(Message.SOMETHING_WRONG, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Override
    public ResponseModel getAllLeaveDetails(EmployeePaginationModel employeePaginationModel) {
        try {
            Page<LeaveApply> leaveApplies;
            Pageable page;
            if (employeePaginationModel.getSortOrder().equals("asc")) {
                page = PageRequest.of(employeePaginationModel.getPage(), employeePaginationModel.getLimit(), Sort.by("createdAt").ascending());
            } else {
                page = PageRequest.of(employeePaginationModel.getPage(), employeePaginationModel.getLimit(), Sort.by("createdAt").descending());
            }
            leaveApplies = leaveRepository.findAllLeave(employeePaginationModel.getDepartmentID(), employeePaginationModel.getSearchKeyword(), page);
            if (!leaveApplies.isEmpty()) {
                List<LeaveApplyResponseModel> leaveApplyResponseModels = setLeaveApplyResponseModelListFormLeaveApply(leaveApplies);
                FinalListingModel finalListingModel = new FinalListingModel();
                finalListingModel.setPage(employeePaginationModel.getPage());
                finalListingModel.setLimit(employeePaginationModel.getLimit());
                finalListingModel.setData(leaveApplyResponseModels);
                finalListingModel.setTotalData(leaveApplies.getTotalElements());
                return CommonUtill.create(Message.LEAVE_APPLY_GET_SUCCESSFULLY, finalListingModel, HttpStatus.OK, HttpStatus.OK.value());
            } else {
                return CommonUtill.create(Message.LEAVE_APPLY_NOT_FOUND, null, HttpStatus.OK, HttpStatus.OK.value());
            }
        } catch (Exception e) {
            logger.error("LeaveServiceImpl getAllLeaveDetails ======= {}", e.getMessage());
            return CommonUtill.create(Message.SOMETHING_WRONG, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    private Employee getEmployeeFromEmployeeID(String employeeID) {
        Optional<Employee> employee = employeeRepository.findById(employeeID);
        return employee.isPresent() ? employee.get() : null;
    }

    private LeaveType getLeaveTypeFromLeaveTypeID(String leaveTypeID) {
        Optional<LeaveType> leaveType = leaveTypeRepository.findById(leaveTypeID);
        return leaveType.isPresent() ? leaveType.get() : null;
    }

    private LeaveApplyResponseModel setLeaveApplyResponseModelFromLeaveApply(LeaveApply leaveApply) {
        LeaveApplyResponseModel leaveApplyResponseModel = new LeaveApplyResponseModel();
        leaveApplyResponseModel.setLeaveApplyID(leaveApply.getLeaveApplyID());
        leaveApplyResponseModel.setToDate(leaveApply.getToDate());
        leaveApplyResponseModel.setFromDate(leaveApply.getFromDate());
        leaveApplyResponseModel.setApplyDate(leaveApply.getApplyDate());
        leaveApplyResponseModel.setEmployeeName(leaveApply.getEmployeeID().getFirstName() + " " + leaveApply.getEmployeeID().getLastName());
        leaveApplyResponseModel.setLeaveTypeName(leaveApply.getLeaveTypeID().getLeaveName());
        leaveApplyResponseModel.setLeaveStatus(leaveApply.getLeaveStatus());
        return leaveApplyResponseModel;
    }

    private List<LeaveApplyResponseModel> setLeaveApplyResponseModelListFormLeaveApply(Page<LeaveApply> leaveApplies) {
        List<LeaveApplyResponseModel> leaveApplyResponseModels = new ArrayList<>();
        for (LeaveApply leaveApply : leaveApplies) {
            leaveApplyResponseModels.add(setLeaveApplyResponseModelFromLeaveApply(leaveApply));
        }
        return leaveApplyResponseModels;
    }
}
