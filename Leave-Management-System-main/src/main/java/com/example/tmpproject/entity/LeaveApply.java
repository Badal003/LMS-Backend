package com.example.tmpproject.entity;


import com.example.tmpproject.enums.DeleteStatus;
import com.example.tmpproject.enums.LeaveStatus;
import com.example.tmpproject.enums.Status;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Calendar;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
public class LeaveApply extends AbstractDomain {
    @Id
    private String leaveApplyID;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Calendar fromDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Calendar toDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Calendar applyDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Calendar remarkDate;
    @Enumerated(EnumType.STRING)
    private LeaveStatus leaveStatus;
    //private int leavetypeId;
    private String remark;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "leaveTypeID", referencedColumnName = "leaveTypeID")
    private LeaveType leaveTypeID;
    //private int employeeId;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "employeeID", referencedColumnName = "employeeID")
    private Employee employeeID;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "managerID", referencedColumnName = "employeeID")
    private Employee managerID;

    public LeaveApply() {
        super();
        this.leaveApplyID = UUID.randomUUID().toString();
        this.setMarkDelete(DeleteStatus.N);
        this.setStatus(Status.ACTIVE);
    }
}
