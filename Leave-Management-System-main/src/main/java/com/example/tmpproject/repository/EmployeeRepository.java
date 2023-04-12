package com.example.tmpproject.repository;


import com.example.tmpproject.entity.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
    @Query("SELECT e FROM Employee e WHERE e.markDelete = 'Y' AND e.status='ACTIVE' AND e.departmentID= :departmentID AND e.firstName LIKE :searchKeyword")
    Page<Employee> findAllEmployee(String departmentID, String searchKeyword, Pageable page);

    Optional<Employee> findByEmailIdAndPassword(String emailId, String password);

    Optional<Employee> findByEmailId(String emailId);
}
