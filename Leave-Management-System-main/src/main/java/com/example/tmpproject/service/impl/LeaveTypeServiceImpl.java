package com.example.tmpproject.service.impl;

import com.example.tmpproject.Model.FinalListingModel;
import com.example.tmpproject.Model.LeaveTypeModel;
import com.example.tmpproject.Model.LeaveTypeResponseModel;
import com.example.tmpproject.Model.PaginationModel;
import com.example.tmpproject.Utill.CommonUtill;
import com.example.tmpproject.Utill.Message;
import com.example.tmpproject.Utill.ResponseModel;
import com.example.tmpproject.entity.LeaveType;
import com.example.tmpproject.enums.DeleteStatus;
import com.example.tmpproject.enums.Status;
import com.example.tmpproject.repository.LeaveTypeRepository;
import com.example.tmpproject.service.LeaveTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LeaveTypeServiceImpl implements LeaveTypeService {
    private static final Logger logger = LoggerFactory.getLogger(DepartmentServiceImpl.class);
    @Autowired
    private LeaveTypeRepository leaveTypeRepository;

    @Override
    public ResponseModel createUpdateLeaveType(LeaveTypeModel leaveTypeModel) {
        try {
            LeaveType leaveType = new LeaveType();
            boolean isUpdated = false;
            if (!leaveTypeModel.getLeaveTypeID().trim().isEmpty() && leaveTypeModel.getLeaveTypeID() != null) {
                leaveType = leaveTypeRepository.getById(leaveTypeModel.getLeaveTypeID());
                isUpdated = true;
                if (leaveType == null) {
                    return CommonUtill.create(Message.PARAMETER_NOT_SUPPLIED, null, HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value());
                }
            }
            leaveTypeModel.setLeaveTypeID(leaveType.getLeaveTypeID());
            BeanUtils.copyProperties(leaveTypeModel, leaveType);
            leaveType = leaveTypeRepository.save(leaveType);
            return CommonUtill.create(isUpdated ? Message.LEAVE_TYPE_UPDATED_SUCCESSFULLY : Message.LEAVE_TYPE_CREATED_SUCCESSFULLY, leaveType.getLeaveTypeID(), HttpStatus.OK, HttpStatus.OK.value());
        } catch (Exception e) {
            logger.error("LeaveTypeServiceImpl createUpdateLeaveType ======= {}", e.getMessage());
            return CommonUtill.create(Message.SOMETHING_WRONG, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Override
    public ResponseModel updateLeaveTypeStatus(String leaveTypeID) {
        try {
            Optional<LeaveType> leaveType = leaveTypeRepository.findById(leaveTypeID);
            if (leaveType.isPresent()) {
                leaveType.get().setStatus(Status.DEACTIVATE);
                leaveType.get().setMarkDelete(DeleteStatus.Y);
                leaveTypeRepository.save(leaveType.get());
                return CommonUtill.create(Message.LEAVE_TYPE_STATUS_UPDATED_SUCCESSFULLY, leaveType.get().getLeaveTypeID(), HttpStatus.OK, HttpStatus.OK.value());
            } else {
                return CommonUtill.create(Message.PARAMETER_NOT_SUPPLIED, null, HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value());
            }
        } catch (Exception e) {
            logger.error("LeaveTypeServiceImpl updateLeaveTypeStatus ======= {}", e.getMessage());
            return CommonUtill.create(Message.SOMETHING_WRONG, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Override
    public ResponseModel getLeaveType(String leaveTypeID) {
        try {
            Optional<LeaveType> leaveType = leaveTypeRepository.findById(leaveTypeID);
            if (leaveType.isPresent()) {
                LeaveTypeResponseModel leaveTypeResponseModel = setLeaveTypeResponseModelFromLeaveType(leaveType.get());
                return CommonUtill.create(Message.LEAVE_TYPE_FOUND, leaveTypeResponseModel, HttpStatus.OK, HttpStatus.OK.value());
            } else {
                return CommonUtill.create(Message.PARAMETER_NOT_SUPPLIED, null, HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value());
            }
        } catch (Exception e) {
            logger.error("LeaveTypeServiceImpl getLeaveType ======= {}", e.getMessage());
            return CommonUtill.create(Message.SOMETHING_WRONG, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Override
    public ResponseModel getLeaveTypes(PaginationModel paginationModel) {
        try {
            Pageable page = CommonUtill.getPaginationDetails(paginationModel);
            Page<LeaveType> leaveTypes = leaveTypeRepository.findAllLeaveType("%" + paginationModel.getSearchKeyword() + "%", page);
            if (!leaveTypes.isEmpty()) {
                List<LeaveTypeResponseModel> leaveTypeResponseModels = setLeaveTypeResponseModelListFromLeaveTypeList(leaveTypes);
                FinalListingModel finalListingModel = new FinalListingModel();
                finalListingModel.setPage(paginationModel.getPage());
                finalListingModel.setLimit(paginationModel.getLimit());
                finalListingModel.setData(leaveTypeResponseModels);
                finalListingModel.setTotalData(leaveTypes.getTotalElements());
                return CommonUtill.create(Message.LEAVE_TYPE_GET_SUCCESSFULLY, finalListingModel, HttpStatus.OK, HttpStatus.OK.value());
            } else {
                return CommonUtill.create(Message.LEAVE_TYPE_NOT_FOUND, null, HttpStatus.OK, HttpStatus.OK.value());
            }
        } catch (Exception e) {
            logger.error("LeaveTypeServiceImpl getLeaveTypes ======= {}", e.getMessage());
            return CommonUtill.create(Message.SOMETHING_WRONG, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    private LeaveTypeResponseModel setLeaveTypeResponseModelFromLeaveType(LeaveType leaveType) {
        LeaveTypeResponseModel leaveTypeResponseModel = new LeaveTypeResponseModel();
        leaveTypeResponseModel.setLeaveTypeID(leaveType.getLeaveTypeID());
        leaveTypeResponseModel.setLeaveName(leaveType.getLeaveName());
        leaveTypeResponseModel.setDescription(leaveType.getDescription());
        leaveTypeResponseModel.setCreatedAt(leaveType.getCreatedAt());
        return leaveTypeResponseModel;
    }

    private List<LeaveTypeResponseModel> setLeaveTypeResponseModelListFromLeaveTypeList(Page<LeaveType> leaveTypes) {
        List<LeaveTypeResponseModel> leaveTypeResponseModels = new ArrayList<>();
        for (LeaveType leaveType : leaveTypes) {
            leaveTypeResponseModels.add(setLeaveTypeResponseModelFromLeaveType(leaveType));
        }
        return leaveTypeResponseModels;
    }
}
