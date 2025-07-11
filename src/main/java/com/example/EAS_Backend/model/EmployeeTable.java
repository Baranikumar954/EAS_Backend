package com.example.EAS_Backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "employee_db")
public class EmployeeTable {
    @Id
    private int employeeId;
    private String employeeName;
    private String employeeDepartment;
    private String employeeMail;
    private LocalDate dateOfJoining;

    public EmployeeTable() {
    }

    public EmployeeTable(int employeeId, String employeeName, String employeeDepartment, String employeeMail, LocalDate dateOfJoining) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.employeeDepartment = employeeDepartment;
        this.employeeMail = employeeMail;
        this.dateOfJoining = dateOfJoining;
    }
}
