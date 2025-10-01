package com.example.est_bootcamp.api;

import com.example.est_bootcamp.att.Attendance;
import com.example.est_bootcamp.emp.Employee;
import com.example.est_bootcamp.service.AttendanceService;
import com.example.est_bootcamp.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
public class AttendanceController {
    private final AttendanceService attendanceService;
    private final EmployeeService employeeService;

    /** 출근 처리 */
    @PostMapping("/{empId}/check-in")
    public Attendance checkIn(@PathVariable Long empId,
                              @RequestHeader(value = "X-Forwarded-For", required = false) String clientIp) {
        Employee emp = employeeService.getById(empId);
        return attendanceService.checkIn(emp, clientIp);
    }

    /** 퇴근 처리 */
    @PostMapping("/{empId}/check-out")
    public Attendance checkOut(@PathVariable Long empId,
                               @RequestHeader(value = "X-Forwarded-For", required = false) String clientIp) {
        Employee emp = employeeService.getById(empId);
        return attendanceService.checkOut(emp, clientIp);
    }

    /** 특정 직원의 특정 일자 근태 조회 */
    @GetMapping("/{empId}/{date}")
    public Attendance getAttendanceByDate(@PathVariable Long empId,
                                          @PathVariable String date) {
        LocalDate workDate = LocalDate.parse(date);
        return attendanceService.findByEmpAndDate(empId, workDate);
    }

    /** 특정 직원의 근태 이력 (기간별 조회) */
    @GetMapping("/{empId}")
    public List<Attendance> getAttendanceList(@PathVariable Long empId,
                                              @RequestParam(required = false) String start,
                                              @RequestParam(required = false) String end) {
        return attendanceService.findByRange(empId, start, end);
    }

    /** 관리자 전용: 모든 직원 근태 이력 조회 */
    @GetMapping("/all")
    public List<Attendance> getAllAttendance(
            @RequestParam(required = false) String start,
            @RequestParam(required = false) String end) {
        return attendanceService.findAllByRange(start, end);
    }
}
