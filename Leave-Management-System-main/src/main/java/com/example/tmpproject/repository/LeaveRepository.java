package com.example.tmpproject.repository;

import com.example.tmpproject.entity.LeaveApply;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;



@Repository
public interface LeaveRepository extends JpaRepository<LeaveApply, String> {
    @Query("SELECT l FROM LeaveApply l WHERE l.markDelete='N' AND l.status='ACTIVE' AND l.employeeID.departmentID.departmentID = :departmentID AND (l.employeeID.firstName = :searchKeyword OR l.employeeID.lastName = :searchKeyword OR l.leaveTypeID.leaveName = :searchKeyword)")
    Page<LeaveApply> findAllLeave(String departmentID, String searchKeyword, Pageable page);
}
