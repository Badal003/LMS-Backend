package com.example.tmpproject.entity;

import com.example.tmpproject.enums.DeleteStatus;
import com.example.tmpproject.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
public class Department extends AbstractDomain {
    @Id
    private String departmentID;
    private String shortName;
    private String fullName;

    public Department() {
        super();
        this.departmentID = UUID.randomUUID().toString();
        this.setMarkDelete(DeleteStatus.N);
        this.setStatus(Status.ACTIVE);
    }

}
