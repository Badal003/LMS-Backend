/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.tmpproject.enums;

import lombok.Getter;

/**
 * @author Majid
 */
@Getter
public enum DeleteStatus {
    Y("Y"),
    N("N");

    private String status;

    private DeleteStatus(String status) {
        this.status = status;
    }
}
