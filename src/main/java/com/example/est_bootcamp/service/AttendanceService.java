package com.example.est_bootcamp.service;

import com.example.est_bootcamp.att.Attendance;
import com.example.est_bootcamp.emp.Employee;
import com.example.est_bootcamp.repo.AttendanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.*;

@Service
@RequiredArgsConstructor
public class AttendanceService {
    private final AttendanceRepository repo;

    @Transactional
    public Attendance checkIn(Employee emp) {
        var today = LocalDate.now(ZoneId.of("Asia/Seoul"));
        Attendance att = repo.findByEmployeeIdAndWorkDate(emp.getId(), today).orElse(
                Attendance.builder().id(null).employee(emp).workDate(today).useYn("Y").build()
        );
        att.setCheckIn(LocalDateTime.now());
        return repo.save(att);
    }

    @Transactional
    public Attendance checkOut(Employee emp) {
        var today = LocalDate.now(ZoneId.of("Asia/Seoul"));
        Attendance att = repo.findByEmployeeIdAndWorkDate(emp.getId(), today).orElseThrow();
        att.setCheckOut(LocalDateTime.now());
        return repo.save(att);
    }
}