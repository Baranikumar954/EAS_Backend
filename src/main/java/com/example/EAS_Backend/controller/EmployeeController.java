package com.example.EAS_Backend.controller;

import com.example.EAS_Backend.model.EmployeeTable;
import com.example.EAS_Backend.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class EmployeeController {
    @Autowired
    AdminService adminService;

    @PostMapping("/manage/addEmployee")
    public Map<String,String> addEmployee(@RequestBody EmployeeTable employee){
        return adminService.addEmployee(employee);
    }

    @PostMapping("/manage/deleteEmployee")
    public Map<String,String> deleteEmployee(@RequestBody EmployeeTable employee){
        return adminService.deleteEmployee(employee);
    }

    @PostMapping("/manage/updateEmployee")
    public Map<String,String> updateEmployee(@RequestBody EmployeeTable employee){
        return adminService.updateEmployee(employee);
    }

}
