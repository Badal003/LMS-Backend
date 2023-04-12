package com.example.tmpproject.service.impl;

import com.example.tmpproject.Model.DepartmentModel;
import com.example.tmpproject.Model.DepartmentResponseModel;
import com.example.tmpproject.Model.FinalListingModel;
import com.example.tmpproject.Model.PaginationModel;
import com.example.tmpproject.Utill.CommonUtill;
import com.example.tmpproject.Utill.Message;
import com.example.tmpproject.Utill.ResponseModel;
import com.example.tmpproject.entity.Department;
import com.example.tmpproject.enums.DeleteStatus;
import com.example.tmpproject.enums.Status;
import com.example.tmpproject.repository.DepartmentRepository;
import com.example.tmpproject.service.DepartmentService;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;
    private static final Logger logger = LoggerFactory.getLogger(DepartmentServiceImpl.class);

    @Override
    public ResponseModel createUpdateDepartment(DepartmentModel departmentModel) {
        try {
            Department department = new Department();
            boolean isUpdated = false;
            if (!departmentModel.getDepartmentID().isEmpty() && departmentModel.getDepartmentID() != null) {
                department = departmentRepository.getById(departmentModel.getDepartmentID());
                if (department == null) {
                    return CommonUtill.create(Message.DEPARTMENT_NOT_FOUND, null, HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value());
                }
                isUpdated = true;
            }
            departmentModel.setDepartmentID(department.getDepartmentID());
            BeanUtils.copyProperties(departmentModel, department);
            department = departmentRepository.save(department);
            return CommonUtill.create(isUpdated ? Message.DEPARTMENT_UPDATED_SUCCESSFULLY : Message.DEPARTMENT_CREATED_SUCCESSFULLY, department.getDepartmentID(), HttpStatus.OK, HttpStatus.OK.value());
        } catch (Exception e) {
            logger.error("DepartmentServiceImpl createUpdateDepartment ======= {}", e.getMessage());
            return CommonUtill.create(Message.SOMETHING_WRONG, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Override
    public ResponseModel updateDepartmentStatus(String departmentID) {
        try {
            Optional<Department> department = departmentRepository.findById(departmentID);
            if (department.isPresent()) {
                department.get().setStatus(Status.DEACTIVATE);
                department.get().setMarkDelete(DeleteStatus.Y);
                departmentRepository.save(department.get());
                return CommonUtill.create(Message.DEPARTMENT_STATUS_UPDATED_SUCCESSFULLY, department.get().getDepartmentID(), HttpStatus.OK, HttpStatus.OK.value());
            } else {
                return CommonUtill.create(Message.PARAMETER_NOT_SUPPLIED, null, HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value());
            }
        } catch (Exception e) {
            logger.error("DepartmentServiceImpl updateDepartmentStatus ======= {}", e.getMessage());
            return CommonUtill.create(Message.SOMETHING_WRONG, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Override
    public ResponseModel getDepartment(String departmentID) {
        try {
            Optional<Department> department = departmentRepository.findById(departmentID);
            if (department.isPresent()) {
                DepartmentResponseModel departmentResponseModel = setDepartmentResponseModelFromDepartment(department.get());
                return CommonUtill.create(Message.DEPARTMENT_FOUND, departmentResponseModel, HttpStatus.OK, HttpStatus.OK.value());
            } else {
                return CommonUtill.create(Message.PARAMETER_NOT_SUPPLIED, null, HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value());
            }
        } catch (Exception e) {
            logger.error("DepartmentServiceImpl getDepartment ======= {}", e.getMessage());
            return CommonUtill.create(Message.SOMETHING_WRONG, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Override
    public ResponseModel getDepartments(PaginationModel paginationModel) {
        try {
            Pageable page = CommonUtill.getPaginationDetails(paginationModel);
            Page<Department> departmentList = departmentRepository.findAllDepartment("%" + paginationModel.getSearchKeyword() + "%", page);
            if (!departmentList.isEmpty()) {
                List<DepartmentResponseModel> departmentResponseModels = setDepartmentResponseModelListFromDepartmentList(departmentList);
                FinalListingModel finalListingModel = new FinalListingModel();
                finalListingModel.setPage(paginationModel.getPage());
                finalListingModel.setLimit(paginationModel.getLimit());
                finalListingModel.setData(departmentResponseModels);
                finalListingModel.setTotalData(departmentList.getTotalElements());
                return CommonUtill.create(Message.DEPARTMENT_GET_SUCCESSFULLY, finalListingModel, HttpStatus.OK, HttpStatus.OK.value());
            } else {
                return CommonUtill.create(Message.DEPARTMENT_NOT_FOUND, null, HttpStatus.OK, HttpStatus.OK.value());
            }
        } catch (Exception e) {
            logger.error("DepartmentServiceImpl getDepartments ======= {}", e.getMessage());
            return CommonUtill.create(Message.SOMETHING_WRONG, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    private DepartmentResponseModel setDepartmentResponseModelFromDepartment(Department department) {
        DepartmentResponseModel departmentResponseModel = new DepartmentResponseModel();
        departmentResponseModel.setDepartmentID(department.getDepartmentID());
        departmentResponseModel.setFullName(department.getFullName());
        departmentResponseModel.setShortName(department.getShortName());
        departmentResponseModel.setCreatedAt(department.getCreatedAt());
        return departmentResponseModel;
    }

    private List<DepartmentResponseModel> setDepartmentResponseModelListFromDepartmentList(Page<Department> departmentList) {
        List<DepartmentResponseModel> departmentResponseModels = new ArrayList<>();
        for (Department department : departmentList) {
            departmentResponseModels.add(setDepartmentResponseModelFromDepartment(department));
        }
        return departmentResponseModels;
    }
}
