/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.tmpproject.entity;


import com.example.tmpproject.enums.DeleteStatus;
import com.example.tmpproject.enums.Status;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * @author Majid
 */
@Data
@MappedSuperclass
public class AbstractDomain implements Serializable {

    @Column(updatable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Calendar createdAt;

    private String createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    private Calendar updatedAt;

    private String updatedBy;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private DeleteStatus markDelete;

    @PrePersist
    public void updateCreateddDate() {
        createdAt = updatedAt = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
    }

    @PreUpdate
    public void updateUpdatedDate() {
         updatedAt = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
    }

}

