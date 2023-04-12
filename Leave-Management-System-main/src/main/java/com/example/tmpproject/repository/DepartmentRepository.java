package com.example.tmpproject.repository;

import com.example.tmpproject.entity.Department;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;



@Repository
public interface DepartmentRepository extends JpaRepository<Department, String> {
    @Query("SELECT d FROM Department d WHERE d.markDelete = 'Y' AND d.status='ACTIVE' AND d.fullName LIKE :searchKeyword")
    Page<Department> findAllDepartment(String searchKeyword, Pageable page);
}
