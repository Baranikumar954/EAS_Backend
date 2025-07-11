package com.example.EAS_Backend.repository;

import com.example.EAS_Backend.model.AdminUsers;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AdminRepo extends JpaRepository<AdminUsers,Integer> {

    AdminUsers findByUserName(String username);
}
