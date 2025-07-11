package com.example.EAS_Backend.controller;

import com.example.EAS_Backend.model.AttendanceTable;
import com.example.EAS_Backend.model.TopWorkers;
import com.example.EAS_Backend.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class AttendanceHandleController {
    @Autowired
    AttendanceService attendanceService;

    @PostMapping("/dateAttendance")
    public List<AttendanceTable> dateAttendance(@RequestBody Map<String, LocalDate> request) {
        LocalDate date = request.get("date");
        return attendanceService.getDateAttendance(date); // ✅ Return full list
    }

    @GetMapping("/attendanceAnalysis")
    public List<TopWorkers> monthAttendance(){
        return attendanceService.getMonthAttendanceAnalysis();
    }
}
