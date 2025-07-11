package com.example.EAS_Backend.model;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Data
@Component
@Scope("prototype")
public class TopWorkers {
    private int employeeId;
    private String employeeName;
    private String employeeMail;
    private String employeeDepartment;
    private double totalHours;

    public TopWorkers(int employeeId, String employeeName, String employeeMail, String employeeDepartment, double totalHours) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.employeeMail = employeeMail;
        this.employeeDepartment = employeeDepartment;
        this.totalHours = totalHours;
    }

    // Getters and Setters
}

