package com.example.EAS_Backend.repository;

import com.example.EAS_Backend.model.EmployeeTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepo extends JpaRepository<EmployeeTable,Integer> {

}
