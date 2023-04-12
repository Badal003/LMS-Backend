package com.example.tmpproject.repository;



import com.example.tmpproject.entity.LeaveType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;




@Repository
public interface LeaveTypeRepository extends JpaRepository<LeaveType, String> {
    @Query("SELECT t FROM LeaveType t WHERE t.markDelete = 'Y' AND t.status='ACTIVE' AND t.leaveName LIKE :searchKeyword")
    Page<LeaveType> findAllLeaveType(String searchKeyword, Pageable page);
}
