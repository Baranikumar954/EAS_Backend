package com.example.EAS_Backend.repository;

import com.example.EAS_Backend.model.AttendanceTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepo extends JpaRepository<AttendanceTable,Integer> {

    Optional<AttendanceTable> findByEmployeeIdAndDate(int employeeId, LocalDate today);

    List<AttendanceTable> findAllByDate(LocalDate date);

    @Query(value = """
    SELECT 
        employee_id,
        employee_name,
        employee_mail,
        employee_department,
        total_hours
    FROM (
        SELECT 
            employee_id,
            employee_name,
            employee_mail,
            employee_department,
            SUM(no_hours_work) AS total_hours,
            RANK() OVER (
                PARTITION BY employee_department 
                ORDER BY SUM(no_hours_work) DESC
            ) as dept_rank
        FROM attendance_table
        WHERE MONTH(date) = MONTH(CURDATE()) AND YEAR(date) = YEAR(CURDATE())
        GROUP BY employee_id, employee_name, employee_mail, employee_department
    ) ranked
    WHERE dept_rank <= 2
    """, nativeQuery = true)
    List<Object[]> findTop2PerDepartmentThisMonth();

    List<AttendanceTable> findByDateBetween(LocalDate startDate, LocalDate endDate);
}
