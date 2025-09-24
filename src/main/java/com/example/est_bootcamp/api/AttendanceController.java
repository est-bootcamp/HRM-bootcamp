package com.example.est_bootcamp.api;

import com.example.est_bootcamp.att.Attendance;
import com.example.est_bootcamp.emp.Employee;
import com.example.est_bootcamp.service.AttendanceService;
import com.example.est_bootcamp.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
public class AttendanceController {
    private final AttendanceService attendanceService;
    private final EmployeeService employeeService;

    @PostMapping("/{empId}/check-in")
    public Attendance checkIn(@PathVariable Long empId) {
        Employee emp = employeeService.getById(empId);
        return attendanceService.checkIn(emp);
    }

    @PostMapping("/{empId}/check-out")
    public Attendance checkOut(@PathVariable Long empId) {
        Employee emp = employeeService.getById(empId);
        return attendanceService.checkOut(emp);
    }
}