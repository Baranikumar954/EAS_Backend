package com.example.EAS_Backend.controller;

import com.example.EAS_Backend.model.AdminUsers;
import com.example.EAS_Backend.model.LoginReport;
import com.example.EAS_Backend.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping({"/login", "/manageLogin"})
    public LoginReport login(@RequestBody Map<String, String> body) {
        String userName = body.get("userName");
        String password = body.get("password");


        AdminUsers admin = adminService.getAdminInfoByUserName(userName);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
        LoginReport report = new LoginReport();

        if (admin == null) {
            report.setStatus("Failed");
            report.setMessage("User not found");
            return report;
        }

        if (!encoder.matches(password, admin.getPassword())) {
            report.setStatus("Failed");
            report.setMessage("Invalid password");
            return report;
        }

        String role = admin.getRole();
        if (!("ADMIN".equals(role) || "CO_ADMIN".equals(role))) {
            report.setStatus("Failed");
            report.setMessage("Unauthorized role");
            return report;
        }

        report.setStatus("Success");
        report.setMessage("Login successful");
        report.setUserName(admin.getUserName());
        report.setRole(role);

        return report;
    }

    @GetMapping("/manage/downloadAllEmployees")
    public ResponseEntity<byte[]> downloadEmployeesAsCSV() {
        return adminService.getAllEmployeesAsCSV();
    }

    @PostMapping("/manage/downloadByDateAttendance")
    public ResponseEntity<byte[]> downloadByDateAttendanceAsCSV(@RequestBody Map<String,String> request){
        LocalDate startDate = LocalDate.parse(request.get("startDate"));
        LocalDate endDate = LocalDate.parse(request.get("endDate"));
        return adminService.getDateAttendanceAsCSV(startDate,endDate);
    }

    @GetMapping("/manage/downloadAllAttendance")
    public ResponseEntity<byte[]> downloadAllAttendanceAsCSV(){
        return adminService.getAllAttendanceAsCSV();
    }
}
