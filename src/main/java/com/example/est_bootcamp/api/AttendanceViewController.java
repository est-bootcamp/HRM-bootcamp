package com.example.est_bootcamp.api;

import com.example.est_bootcamp.security.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AttendanceViewController {

    @GetMapping("/attendance")
    public String showAttendancePage(Model model) {
        // 현재 로그인 사용자 정보 가져오기
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object principal = auth.getPrincipal();

        Long empId = null;
        String empName = null;
        String role = "USER"; // 기본값

        if (principal instanceof CustomUserDetails userDetails) {
            empId = userDetails.getEmpId();
            empName = userDetails.getEmpName();
            role = userDetails.getRole();  // "ADMIN" / "USER"
        }

        // 공통 모델 속성
        model.addAttribute("empId", empId != null ? empId : 0L);
        model.addAttribute("empName", empName != null ? empName : "알 수 없음");
        model.addAttribute("role", role);

        // 관리자면 관리자용 페이지로 이동
        if ("ADMIN".equalsIgnoreCase(role)) {
            return "attendance-admin"; // → templates/attendance-admin.html
        }

        // 일반 직원이면 직원용 페이지
        return "attendance";  // → templates/attendance.html
    }
}
