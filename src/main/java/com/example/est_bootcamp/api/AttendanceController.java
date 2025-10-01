package com.example.est_bootcamp.api;

import com.example.est_bootcamp.att.Attendance;
import com.example.est_bootcamp.emp.Employee;
import com.example.est_bootcamp.service.AttendanceService;
import com.example.est_bootcamp.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> checkIn(@PathVariable Long empId,
                                     @RequestHeader(value = "X-Forwarded-For", required = false) String clientIp) {
        try {
            Employee emp = employeeService.getById(empId);
            return ResponseEntity.ok(attendanceService.checkIn(emp, clientIp));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /** 퇴근 처리 */
    @PostMapping("/{empId}/check-out")
    public ResponseEntity<?> checkOut(@PathVariable Long empId,
                                      @RequestHeader(value = "X-Forwarded-For", required = false) String clientIp) {
        try {
            Employee emp = employeeService.getById(empId);
            return ResponseEntity.ok(attendanceService.checkOut(emp, clientIp));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /** 특정 직원의 특정 일자 근태 조회 */
    @GetMapping("/{empId}/{date}")
    public ResponseEntity<Attendance> getAttendanceByDate(@PathVariable Long empId,
                                                          @PathVariable String date) {
        LocalDate workDate = LocalDate.parse(date);
        return ResponseEntity.ok(attendanceService.findByEmpAndDate(empId, workDate));
    }

    /** 특정 직원의 근태 이력 (기간별 조회) */
    @GetMapping("/{empId}")
    public ResponseEntity<List<Attendance>> getAttendanceList(@PathVariable Long empId,
                                                              @RequestParam(required = false) String start,
                                                              @RequestParam(required = false) String end) {
        return ResponseEntity.ok(attendanceService.findByRange(empId, start, end));
    }

    /** 관리자 전용: 모든 직원 근태 이력 조회 */
    @GetMapping("/all")
    public ResponseEntity<List<Attendance>> getAllAttendance(
            @RequestParam(required = false) String start,
            @RequestParam(required = false) String end) {
        return ResponseEntity.ok(attendanceService.findAllByRange(start, end));
    }
}
