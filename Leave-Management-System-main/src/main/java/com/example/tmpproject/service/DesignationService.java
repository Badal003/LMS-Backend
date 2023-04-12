package com.example.tmpproject.service;

import com.example.tmpproject.Model.DesignationModel;
import com.example.tmpproject.Model.PaginationModel;
import com.example.tmpproject.Utill.ResponseModel;

public interface DesignationService {
    ResponseModel createUpdateDesignation(DesignationModel designationModel);

    ResponseModel updateDesignationStatus(String designationID);

    ResponseModel getDesignation(String designationID);

    ResponseModel getDesignations(PaginationModel paginationModel);
}
