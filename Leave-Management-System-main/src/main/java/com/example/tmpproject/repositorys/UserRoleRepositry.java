package com.example.tmpproject.repositorys;

import com.example.tmpproject.oldentity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepositry extends JpaRepository<UserRole,Integer>
{
    public UserRole findById(int ur_id);
    public List<UserRole> findAllBy();
    public UserRole deleteById(int ur_id);
}
