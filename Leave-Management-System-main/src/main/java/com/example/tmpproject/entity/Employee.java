package com.example.tmpproject.entity;

import com.example.tmpproject.enums.DeleteStatus;
import com.example.tmpproject.enums.Status;
import com.example.tmpproject.enums.UserRole;
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
public class Employee extends AbstractDomain {
    @Id
    private String employeeID;
    private String firstName;
    private String middleName;
    private String lastName;
    private String gender;
    private String emailId;
    private String mobileNumber;
    private String password;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Calendar dateOfBirth;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @Temporal(TemporalType.DATE)
    private Calendar dateOfJoin;

    //private int departmentId;
    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "departmentID", referencedColumnName = "departmentID")
    private Department departmentID;
    //private int designationId;
    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "designationID", referencedColumnName = "designationID")
    private Designation designationID;
    //private int userroleId;
    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    public Employee() {
        super();
        this.employeeID = UUID.randomUUID().toString();
        this.setMarkDelete(DeleteStatus.N);
        this.setStatus(Status.ACTIVE);
    }
}
