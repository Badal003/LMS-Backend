package com.example.tmpproject.Model;

import lombok.Data;

@Data
public class EmployeePaginationModel {
    private int limit;
    private int page;
    private String searchKeyword;
    private String sortField;
    private String sortOrder;
    private String departmentID;
    private String designationID;
}
