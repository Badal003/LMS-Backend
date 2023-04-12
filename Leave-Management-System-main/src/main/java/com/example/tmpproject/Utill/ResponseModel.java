package com.example.tmpproject.Utill;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

/**
 *
 * @author henil
 */
@Getter
@Setter
@ToString
public class ResponseModel {
    private HttpStatus status;
    private int statusCode;
    private String message;
    private Object data;
}
