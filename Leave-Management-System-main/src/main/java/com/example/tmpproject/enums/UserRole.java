package com.example.tmpproject.enums;

import lombok.Getter;

@Getter
public enum UserRole {
    ADMIN("admin"),
    EMPLOYEE("employee"),
    MANAGER("manager");
    private String desc;

    UserRole(String desc) { this.desc = desc;}
}
