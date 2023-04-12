package com.example.tmpproject.repository;

import com.example.tmpproject.entity.Designation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface DesignationRepository extends JpaRepository<Designation, String> {
    @Query("SELECT d FROM Designation d WHERE d.markDelete = 'Y' AND d.status='ACTIVE' AND d.name LIKE :searchKeyword")
    Page<Designation> findAllDesignation(String searchKeyword, Pageable page);
}
