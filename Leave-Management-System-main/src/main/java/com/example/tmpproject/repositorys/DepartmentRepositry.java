package com.example.tmpproject.repositorys;

import com.example.tmpproject.oldentity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepositry extends JpaRepository<Department,Integer>
{
    Department findById(int depart_id);
    List<Department> findAllBy();
    Department deleteById(int depart_id);
}
