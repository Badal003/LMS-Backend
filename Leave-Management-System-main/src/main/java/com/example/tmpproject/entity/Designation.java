package com.example.tmpproject.entity;


import com.example.tmpproject.enums.DeleteStatus;
import com.example.tmpproject.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
public class Designation extends AbstractDomain {
    @Id
    private String designationID;

    private String name;

    private String description;

    public Designation() {
        super();
        this.designationID = UUID.randomUUID().toString();
        this.setMarkDelete(DeleteStatus.N);
        this.setStatus(Status.ACTIVE);
    }
}
