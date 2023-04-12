package com.example.tmpproject.service.impl;

import com.example.tmpproject.Model.DesignationModel;
import com.example.tmpproject.Model.DesignationResponseModel;
import com.example.tmpproject.Model.FinalListingModel;
import com.example.tmpproject.Model.PaginationModel;
import com.example.tmpproject.Utill.CommonUtill;
import com.example.tmpproject.Utill.Message;
import com.example.tmpproject.Utill.ResponseModel;
import com.example.tmpproject.entity.Designation;
import com.example.tmpproject.enums.DeleteStatus;
import com.example.tmpproject.enums.Status;
import com.example.tmpproject.repository.DesignationRepository;
import com.example.tmpproject.service.DesignationService;
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
public class DesignationServiceImpl implements DesignationService {
    private static final Logger logger = LoggerFactory.getLogger(DesignationServiceImpl.class);
    @Autowired
    private DesignationRepository designationRepository;

    @Override
    public ResponseModel createUpdateDesignation(DesignationModel designationModel) {
        try {
            Designation designation = new Designation();
            boolean isUpdated = false;
            if (!designationModel.getDesignationID().trim().isEmpty() && designationModel.getDesignationID() != null) {
                designation = designationRepository.getById(designationModel.getDesignationID());
                isUpdated = true;
                if (designation == null) {
                    return CommonUtill.create(Message.PARAMETER_NOT_SUPPLIED, null, HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value());
                }
            }
            designationModel.setDesignationID(designation.getDesignationID());
            BeanUtils.copyProperties(designationModel, designation);
            designation = designationRepository.save(designation);
            return CommonUtill.create(isUpdated ? Message.DESIGNATION_UPDATED_SUCCESSFULLY : Message.DESIGNATION_CREATED_SUCCESSFULLY, designation.getDesignationID(), HttpStatus.OK, HttpStatus.OK.value());
        } catch (Exception e) {
            logger.error("DesignationServiceImpl createUpdateDesignation ======= {}", e.getMessage());
            return CommonUtill.create(Message.SOMETHING_WRONG, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Override
    public ResponseModel updateDesignationStatus(String designationID) {
        try {
            Optional<Designation> designation = designationRepository.findById(designationID);
            if (designation.isPresent()) {
                designation.get().setStatus(Status.DEACTIVATE);
                designation.get().setMarkDelete(DeleteStatus.Y);
                designationRepository.save(designation.get());
                return CommonUtill.create(Message.DESIGNATION_STATUS_UPDATED_SUCCESSFULLY, designation.get().getDesignationID(), HttpStatus.OK, HttpStatus.OK.value());
            } else {
                return CommonUtill.create(Message.PARAMETER_NOT_SUPPLIED, null, HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value());
            }
        } catch (Exception e) {
            logger.error("DesignationServiceImpl updateDesignationStatus ======= {}", e.getMessage());
            return CommonUtill.create(Message.SOMETHING_WRONG, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Override
    public ResponseModel getDesignation(String designationID) {
        try {
            Optional<Designation> designation = designationRepository.findById(designationID);
            if (designation.isPresent()) {
                DesignationResponseModel designationResponseModel = setDesignationResponseModelFromDesignation(designation.get());
                return CommonUtill.create(Message.DESIGNATION_FOUND, designationResponseModel, HttpStatus.OK, HttpStatus.OK.value());
            } else {
                return CommonUtill.create(Message.PARAMETER_NOT_SUPPLIED, null, HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value());
            }
        } catch (Exception e) {
            logger.error("DesignationServiceImpl getDesignation ======= {}", e.getMessage());
            return CommonUtill.create(Message.SOMETHING_WRONG, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    @Override
    public ResponseModel getDesignations(PaginationModel paginationModel) {
        try {
            Pageable page = CommonUtill.getPaginationDetails(paginationModel);
            Page<Designation> designations = designationRepository.findAllDesignation("%" + paginationModel.getSearchKeyword() + "%", page);
            if (!designations.isEmpty()) {
                List<DesignationResponseModel> designationResponseModels = setDesignationResponseModelListFromDesignationList(designations);
                FinalListingModel finalListingModel = new FinalListingModel();
                finalListingModel.setPage(paginationModel.getPage());
                finalListingModel.setLimit(paginationModel.getLimit());
                finalListingModel.setData(designationResponseModels);
                finalListingModel.setTotalData(designations.getTotalElements());
                return CommonUtill.create(Message.DESIGNATION_GET_SUCCESSFULLY, finalListingModel, HttpStatus.OK, HttpStatus.OK.value());
            } else {
                return CommonUtill.create(Message.DESIGNATION_NOT_FOUND, null, HttpStatus.OK, HttpStatus.OK.value());
            }

        } catch (Exception e) {
            logger.error("DesignationServiceImpl getDesignations ======= {}", e.getMessage());
            return CommonUtill.create(Message.SOMETHING_WRONG, e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value());
        }
    }

    private DesignationResponseModel setDesignationResponseModelFromDesignation(Designation designation) {
        DesignationResponseModel designationResponseModel = new DesignationResponseModel();
        designationResponseModel.setDesignationID(designation.getDesignationID());
        designationResponseModel.setName(designation.getName());
        designationResponseModel.setDescription(designation.getDescription());
        designationResponseModel.setCreatedAt(designation.getCreatedAt());
        return designationResponseModel;
    }

    private List<DesignationResponseModel> setDesignationResponseModelListFromDesignationList(Page<Designation> designations) {
        List<DesignationResponseModel> designationResponseModels = new ArrayList<>();
        for (Designation designation : designations) {
            designationResponseModels.add(setDesignationResponseModelFromDesignation(designation));
        }
        return designationResponseModels;
    }
}
