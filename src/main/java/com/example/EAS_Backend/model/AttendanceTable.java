package com.example.EAS_Backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
public class AttendanceTable {
    @Id
    private int employeeId;
    private String employeeName;
    private String employeeDepartment;
    private String employeeMail;
    private LocalTime checkInTime;
    private LocalTime checkOutTime;
    private LocalDate date;
    private Double noHoursWork;

    public void calculateHours() {
        if (checkInTime != null && checkOutTime != null) {
            Duration duration = Duration.between(checkInTime, checkOutTime);
            this.noHoursWork = duration.toMinutes() / 60.0;
        } else {
            this.noHoursWork = 0.0;
        }
    }
}
