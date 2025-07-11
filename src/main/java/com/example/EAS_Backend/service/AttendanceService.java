package com.example.EAS_Backend.service;

import com.example.EAS_Backend.model.AttendanceTable;
import com.example.EAS_Backend.model.EmployeeTable;
import com.example.EAS_Backend.model.TopWorkers;
import com.example.EAS_Backend.repository.AttendanceRepo;
import com.example.EAS_Backend.repository.EmployeeRepo;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class AttendanceService {

    @Autowired
    AttendanceRepo attendanceRepo;

    @Autowired
    EmployeeRepo employeeRepo;
    public Optional<AttendanceTable> findByEmployeeIdAndDate(int employeeId, LocalDate today) {
        return attendanceRepo.findByEmployeeIdAndDate(employeeId,today);
    }

    @Transactional(readOnly = true)
    public Optional<EmployeeTable> findById(int employeeId) {
        return employeeRepo.findById(employeeId);
    }

    public void save(AttendanceTable attendance) {
        attendanceRepo.save(attendance);
    }

    @Transactional(readOnly = true)
    public List<AttendanceTable> getDateAttendance(LocalDate date) {
        return attendanceRepo.findAllByDate(date); //
    }

    public List<TopWorkers> getMonthAttendanceAnalysis() {
        List<Object[]> rawData = attendanceRepo.findTop2PerDepartmentThisMonth();

        return rawData.stream()
                .map(obj -> new TopWorkers(
                        ((Number) obj[0]).intValue(),     // safer type casting
                        (String) obj[1],
                        (String) obj[2],
                        (String) obj[3],
                        ((Number) obj[4]).doubleValue()
                ))
                .collect(Collectors.toList());

    }
}
