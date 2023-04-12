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
public class LeaveType extends AbstractDomain {
    @Id
    private String leaveTypeID;
    private String leaveName;
    private String description;

    public LeaveType() {
        super();
        this.leaveTypeID = UUID.randomUUID().toString();
        this.setMarkDelete(DeleteStatus.N);
        this.setStatus(Status.ACTIVE);
    }
}
