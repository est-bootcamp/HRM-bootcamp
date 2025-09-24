package com.example.est_bootcamp.service;

import com.example.est_bootcamp.att.Attendance;
import com.example.est_bootcamp.emp.Employee;
import com.example.est_bootcamp.repo.AttendanceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.*;

@Service
@RequiredArgsConstructor
public class AttendanceService {
    private final AttendanceMapper mapper;

    public Attendance checkIn(Employee emp) {
        LocalDate today = LocalDate.now(ZoneId.of("Asia/Seoul"));

        Attendance att = mapper.findByEmpAndDate(emp.getEmpId(), today)
                .orElse(Attendance.builder()
                        .empId(emp.getEmpId())   // employee 대신 empId 직접 저장
                        .workDate(today)
                        .useYn("Y")
                        .build()
                );

        att.setCheckIn(LocalDateTime.now());

        if (att.getAttId() == null) {  // PK
            mapper.insert(att);
        } else {
            mapper.update(att);
        }
        return att;
    }

    public Attendance checkOut(Employee emp) {
        LocalDate today = LocalDate.now(ZoneId.of("Asia/Seoul"));

        Attendance att = mapper.findByEmpAndDate(emp.getEmpId(), today)
                .orElseThrow(() -> new IllegalStateException("출근 기록 없음"));

        att.setCheckOut(LocalDateTime.now());
        mapper.update(att);

        return att;
    }
}
