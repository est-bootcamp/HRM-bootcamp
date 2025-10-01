package com.example.est_bootcamp.service;

import com.example.est_bootcamp.att.Attendance;
import com.example.est_bootcamp.emp.Employee;
import com.example.est_bootcamp.repo.AttendanceMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendanceService {
    private final AttendanceMapper mapper;

    /** 출근 처리 */
    public Attendance checkIn(Employee emp, String clientIp) {
        LocalDate today = LocalDate.now(ZoneId.of("Asia/Seoul"));

        Attendance att = mapper.findByEmpAndDate(emp.getEmpId(), today)
                .orElse(null);

        if (att != null && att.getCheckIn() != null) {
            throw new IllegalStateException("이미 출근 기록이 있습니다.");
        }

        if (att == null) {
            att = Attendance.builder()
                    .empId(emp.getEmpId())
                    .workDate(today)
                    .checkIn(LocalDateTime.now())
                    .useYn("Y")
                    .regIp(clientIp != null ? clientIp : "LOCAL")
                    .regDate(LocalDateTime.now())
                    .regUsId(emp.getEmpId())
                    .build();
            mapper.insert(att);
        } else {
            att.setCheckIn(LocalDateTime.now());
            att.setModIp(clientIp != null ? clientIp : "LOCAL");
            att.setModDate(LocalDateTime.now());
            att.setModUsId(emp.getEmpId());
            mapper.update(att);
        }

        return att;
    }

    /** 퇴근 처리 */
    public Attendance checkOut(Employee emp, String clientIp) {
        LocalDate today = LocalDate.now(ZoneId.of("Asia/Seoul"));

        Attendance att = mapper.findByEmpAndDate(emp.getEmpId(), today)
                .orElseThrow(() -> new IllegalStateException("출근 기록 없음"));

        if (att.getCheckOut() != null) {
            throw new IllegalStateException("이미 퇴근 기록이 있습니다.");
        }

        att.setCheckOut(LocalDateTime.now());
        att.setModIp(clientIp != null ? clientIp : "LOCAL");
        att.setModDate(LocalDateTime.now());
        att.setModUsId(emp.getEmpId());
        mapper.update(att);

        return att;
    }

    /** 특정 일자 근태 조회 */
    public Attendance findByEmpAndDate(Long empId, LocalDate workDate) {
        return mapper.findByEmpAndDate(empId, workDate).orElse(null);
    }

    /** 특정 직원 기간별 근태 조회 */
    public List<Attendance> findByRange(Long empId, String start, String end) {
        LocalDate startDate = (start != null && !start.isBlank()) ? LocalDate.parse(start) : LocalDate.now().minusMonths(1);
        LocalDate endDate = (end != null && !end.isBlank()) ? LocalDate.parse(end) : LocalDate.now();
        return mapper.findByRange(empId, startDate, endDate);
    }

    /** 관리자 전용: 모든 직원 근태 조회 */
    public List<Attendance> findAllByRange(String start, String end) {
        LocalDate startDate = (start != null && !start.isBlank()) ? LocalDate.parse(start) : LocalDate.now().minusMonths(1);
        LocalDate endDate = (end != null && !end.isBlank()) ? LocalDate.parse(end) : LocalDate.now();
        return mapper.findAllByRange(startDate, endDate);
    }

    /** 관리자 전용: 특정 날짜 근태 현황판 */
    public List<Attendance> findAllByDate(String date) {
        LocalDate workDate = (date != null && !date.isBlank()) ? LocalDate.parse(date) : LocalDate.now();
        return mapper.findAllByDate(workDate);
    }

    /** 관리자 전용: 특정 직원 전체 근태 */
    public List<Attendance> findAllByEmp(Long empId) {
        return mapper.findAllByEmp(empId);
    }
}
