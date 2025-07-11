package com.example.EAS_Backend.controller;

import com.example.EAS_Backend.model.AttendanceTable;
import com.example.EAS_Backend.model.EmployeeTable;
import com.example.EAS_Backend.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class AttendanceController {

    @Autowired
    AttendanceService attendanceService;


    @PostMapping("/checkIn")
    public Map<String, String> addCheckIn(@RequestBody Map<String, String> request) {
        Map<String, String> response = new HashMap<>();

        try {
            int employeeId = Integer.parseInt(request.get("employeeId"));
            String department = request.get("employeeDepartment");
            LocalDate today = LocalDate.now();

            Optional<AttendanceTable> existing = attendanceService.findByEmployeeIdAndDate(employeeId, today);

            if (existing.isPresent()) {
                response.put("status","Failed");
                response.put("message", "Already checked in today");
                return response;
            }

            Optional<EmployeeTable> employeeOpt = attendanceService.findById(employeeId);

            if (employeeOpt.isPresent()) {
                EmployeeTable employee = employeeOpt.get();

                AttendanceTable attendance = new AttendanceTable();
                attendance.setEmployeeId(employeeId);
                attendance.setEmployeeName(employee.getEmployeeName());
                attendance.setEmployeeDepartment(employee.getEmployeeDepartment());
                attendance.setEmployeeMail(employee.getEmployeeMail());
                attendance.setCheckInTime(LocalTime.now());
                attendance.setDate(today);

                attendanceService.save(attendance);

                response.put("status", "success");
                response.put("message","Check-In time updated successfully");
            } else {
                response.put("status", "Failed");
                response.put("message","Employee does not exist");
            }

        } catch (Exception e) {
            response.put("status", "Error");
            response.put("message",e.getMessage());
        }

        return response;
    }

    @PostMapping("/checkOut")
    public Map<String, String> addCheckOut(@RequestBody Map<String, String> request) {
        Map<String, String> response = new HashMap<>();
        try {
            int employeeId = Integer.parseInt(request.get("employeeId"));
            String department = request.get("employeeDepartment");
            LocalDate today = LocalDate.now();
            LocalTime checkOutTime = LocalTime.now();

            Optional<AttendanceTable> existing = attendanceService.findByEmployeeIdAndDate(employeeId, today);
            Optional<EmployeeTable> employeeOpt = attendanceService.findById(employeeId);

            if (existing.isPresent() && employeeOpt.isPresent()) {
                AttendanceTable attendance = existing.get();
                attendance.setCheckOutTime(checkOutTime);
                attendance.calculateHours();
                attendanceService.save(attendance);
                response.put("status", "success");
                response.put("message", "Checkout time updated successfully.");
            } else {
                response.put("status", "failed");
                response.put("message", "Attendance record not found for today or employee does not exist.");
            }
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "An error occurred: " + e.getMessage());
        }

        return response;
    }



}
