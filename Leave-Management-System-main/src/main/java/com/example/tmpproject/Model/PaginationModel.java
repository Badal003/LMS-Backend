package com.example.tmpproject.Model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 *
 * @author Badal
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaginationModel {

    private int limit;
    private int page;
    private String searchKeyword;
    private String sortField;
    private String sortOrder;

}
