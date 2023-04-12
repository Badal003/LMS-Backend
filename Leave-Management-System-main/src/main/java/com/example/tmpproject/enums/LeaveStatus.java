package com.example.tmpproject.enums;

import lombok.Getter;

@Getter
public enum LeaveStatus {
    APPROVED("approved"),
    NOT_APPROVED("notApproved"),
    PENDING("pending");
    private String desc;

    LeaveStatus(String desc) { this.desc = desc;}
}
