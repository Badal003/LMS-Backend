package com.example.tmpproject.Model;

import lombok.Data;

import java.util.Calendar;

@Data
public class DesignationResponseModel {
    private String designationID;
    private String name;
    private String description;
    private Calendar createdAt;
}
