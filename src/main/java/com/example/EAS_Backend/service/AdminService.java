package com.example.EAS_Backend.service;

import com.example.EAS_Backend.model.AdminUsers;
import com.example.EAS_Backend.model.AttendanceTable;
import com.example.EAS_Backend.model.EmployeeTable;
import com.example.EAS_Backend.repository.AdminRepo;
import com.example.EAS_Backend.repository.AttendanceRepo;
import com.example.EAS_Backend.repository.EmployeeRepo;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class AdminService {

    @Autowired
    AdminRepo repo;

    @Autowired
    EmployeeRepo employeeRepo;

    @Autowired
    AttendanceRepo attendanceRepo;
    public AdminUsers getAdminInfoByUserName(String userName) {
        return repo.findByUserName(userName);
    }

    public Map<String, String> addEmployee(EmployeeTable employee) {
        Map<String,String> response = new HashMap<>();
        try{
            if (employeeRepo.existsById(employee.getEmployeeId())) {
                response.put("status", "Conflict");
                response.put("message", "Employee ID already exists");
                return response;
            }
            employee.setDateOfJoining(LocalDate.now());
            employeeRepo.save(employee);
            response.put("status", "Created");
            response.put("message", "Employee added successfully");
        }catch (Exception e){
            response.put("status", "Error");
            response.put("message", "Failed to add employee: " + e.getMessage());
        }
        return response;
    }

    public Map<String, String> deleteEmployee(EmployeeTable employee) {
        Map<String,String> response = new HashMap<>();
        try{

            if(employeeRepo.existsById(employee.getEmployeeId())){
                employeeRepo.deleteById(employee.getEmployeeId());
                response.put("status", "Deleted");
                response.put("message", "Employee deleted successfully");
            }
        }catch (Exception e){
            response.put("status", "Error");
            response.put("message", "Failed to delete employee: " + e.getMessage());
        }
        return response;
    }

    public Map<String, String> updateEmployee(EmployeeTable employee) {
        Map<String, String> response = new HashMap<>();
        try {
            if (employeeRepo.existsById(employee.getEmployeeId())) {
                EmployeeTable existingEmployee = employeeRepo.findById(employee.getEmployeeId()).orElse(null);
                if (existingEmployee != null) {
                    existingEmployee.setEmployeeName(employee.getEmployeeName());
                    existingEmployee.setEmployeeMail(employee.getEmployeeMail());
                    existingEmployee.setEmployeeDepartment(employee.getEmployeeDepartment());
                    // Note: dateOfJoining is NOT updated here

                    employeeRepo.save(existingEmployee);
                    response.put("status", "Updated");
                    response.put("message", "Employee updated successfully");
                } else {
                    response.put("status", "NotFound");
                    response.put("message", "Employee not found");
                }
            } else {
                response.put("status", "NotFound");
                response.put("message", "Employee ID does not exist");
            }
        } catch (Exception e) {
            response.put("status", "Error");
            response.put("message", "Failed to update employee: " + e.getMessage());
        }
        return response;
    }
    public ResponseEntity<byte[]> getAllEmployeesAsCSV() {
        List<EmployeeTable> employees = employeeRepo.findAll();

        StringBuilder csvBuilder = new StringBuilder();
        csvBuilder.append("Employee ID,Name,Email,Department,Date of Joining\n");

        for (EmployeeTable emp : employees) {
            csvBuilder.append(emp.getEmployeeId()).append(",")
                    .append(emp.getEmployeeName()).append(",")
                    .append(emp.getEmployeeMail()).append(",")
                    .append(emp.getEmployeeDepartment()).append(",")
                    .append(emp.getDateOfJoining()).append("\n");
        }

        byte[] csvBytes = csvBuilder.toString().getBytes(StandardCharsets.UTF_8);
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
        String filename = "employees_" + timestamp + ".csv";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("text/csv"));
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename);

        return new ResponseEntity<>(csvBytes, headers, HttpStatus.OK);
    }

    public ResponseEntity<byte[]> getAllAttendanceAsCSV() {
        List<AttendanceTable> attendance = attendanceRepo.findAll();
        StringBuilder csvBuilder = new StringBuilder();
        csvBuilder.append("Employee ID,Employee Name,Employee Mail,Employee Department,Date,Check-In,Check-Out,Hours Worked\n");
        for(AttendanceTable attendanceTable:attendance){
            csvBuilder.append(attendanceTable.getEmployeeId()).append(",")
                    .append(attendanceTable.getEmployeeName()).append(",")
                    .append(attendanceTable.getEmployeeMail()).append(",")
                    .append(attendanceTable.getEmployeeDepartment()).append(",")
                    .append(attendanceTable.getDate()).append(",")
                    .append(attendanceTable.getCheckInTime()).append(",")
                    .append(attendanceTable.getCheckOutTime()).append(",")
                    .append(attendanceTable.getNoHoursWork()).append("\n");
        }
        byte[] csvBytes =csvBuilder.toString().getBytes(StandardCharsets.UTF_8);
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
        String filename = "attendance_" + timestamp + ".csv";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("text/csv"));
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename);

        return new ResponseEntity<>(csvBytes, headers, HttpStatus.OK);
    }

    public ResponseEntity<byte[]> getDateAttendanceAsCSV(LocalDate startDate, LocalDate endDate) {
        List<AttendanceTable> attendance = attendanceRepo.findByDateBetween(startDate,endDate);

        StringBuilder csvBuilder = new StringBuilder();
        csvBuilder.append("Employee ID,Employee Name,Employee Mail,Employee Department,Date,Check-In,Check-Out,Hours Worked\n");
        for(AttendanceTable attendanceTable:attendance){
            csvBuilder.append(attendanceTable.getEmployeeId()).append(",")
                    .append(attendanceTable.getEmployeeName()).append(",")
                    .append(attendanceTable.getEmployeeMail()).append(",")
                    .append(attendanceTable.getEmployeeDepartment()).append(",")
                    .append(attendanceTable.getDate()).append(",")
                    .append(attendanceTable.getCheckInTime()).append(",")
                    .append(attendanceTable.getCheckOutTime()).append(",")
                    .append(attendanceTable.getNoHoursWork()).append("\n");
        }
        byte[] csvBytes =csvBuilder.toString().getBytes(StandardCharsets.UTF_8);
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
        String filename = "attendance_" + timestamp + ".csv";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("text/csv"));
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename);

        return new ResponseEntity<>(csvBytes, headers, HttpStatus.OK);
    }
}
