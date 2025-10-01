package com.example.est_bootcamp.api;

import com.example.est_bootcamp.emp.Employee;
import com.example.est_bootcamp.security.CustomUserDetails;
import com.example.est_bootcamp.service.EmployeeService;
import com.example.est_bootcamp.service.DepartmentService;
import com.example.est_bootcamp.service.PositionService;
import com.example.est_bootcamp.service.PageResponse; // PageResponse import
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeePageController {

    private final EmployeeService service;
    private final DepartmentService deptService;
    private final PositionService positionService;

    /**
     * 직원 목록 페이지 (검색 + 페이지네이션)
     * - GET /employees?page=1&size=10&keyword=홍길동
     * - keyword로 이름/이메일/전화번호 검색 가능
     * - 결과를 "list.html" 뷰에 전달
     */
    @GetMapping
    public String list(@RequestParam(defaultValue = "1") int page,
                       @RequestParam(defaultValue = "10") int size,
                       @RequestParam(defaultValue = "") String keyword,
                       Model model) {

        // 서비스에서 페이징 처리된 결과 가져오기
        PageResponse<Employee> employeePage = service.getEmpLstAllPaged(page, size, keyword);

        // 화면에 전달할 데이터 설정
        model.addAttribute("employeePage", employeePage);
        model.addAttribute("keyword", keyword);

        return "list";
    }

    /**
     * 직원 등록 폼 화면
     * - GET /employees/new
     * - 관리자만 접근 가능 (ROLE_ADMIN)
     * - 신규 Employee 객체와 부서/직급 목록을 모델에 담아 "form.html" 뷰에 전달
     */
    @GetMapping("/new")
    @PreAuthorize("hasRole('ADMIN')")
    public String createForm(Model model) {
        model.addAttribute("employee", new Employee());
        model.addAttribute("departments", deptService.getAll());
        model.addAttribute("positions", positionService.getAll());
        return "form";
    }

    /**
     * 직원 수정 폼 화면
     * - GET /employees/{id}/edit
     * - 관리자만 접근 가능
     * - 기존 직원 정보를 조회하여 form에 바인딩
     * - 없는 경우 오류 메시지를 flashAttribute로 전달하고 목록으로 리다이렉트
     */
    @GetMapping("/{id}/edit")
    @PreAuthorize("hasRole('ADMIN')")
    public String editForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttrs) {
        try {
            Employee employee = service.getById(id);
            model.addAttribute("employee", employee);
            model.addAttribute("departments", deptService.getAll());
            model.addAttribute("positions", positionService.getAll());
            return "form";
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("errorMessage", "해당 직원 정보를 찾을 수 없습니다.");
            return "redirect:/employees";
        }
    }

    /**
     * 직원 저장 (등록/수정 공용 처리)
     * - POST /employees
     * - 관리자만 가능
     * - empId가 없으면 신규 등록, 있으면 수정 처리
     * - 필수값(부서, 직급) 누락 시 다시 form으로 이동
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public String save(@ModelAttribute Employee employee, Model model) {
        if (employee.getDprId() == null || employee.getPstId() == null) {
            model.addAttribute("employee", employee);
            model.addAttribute("departments", deptService.getAll());
            model.addAttribute("positions", positionService.getAll());
            model.addAttribute("errorMessage", "부서와 직급은 반드시 선택해야 합니다.");
            return "form";
        }

        // 신규 vs 수정 구분
        if (employee.getEmpId() == null) {
            service.create(employee);
        } else {
            service.update(employee);
        }
        return "redirect:/employees"; // 저장 후 목록으로 이동
    }

    /**
     * 직원 삭제 (소프트 삭제)
     * - POST /employees/{id}/delete
     * - 관리자만 가능
     * - CustomUserDetails에서 로그인 사용자 ID를 가져와 삭제 이력 저장
     * - 삭제 시 실제 삭제가 아닌 use_yn = 'N' 처리
     */
    @PostMapping("/{id}/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public String delete(@PathVariable Long id,
                         HttpServletRequest request,
                         Authentication authentication,
                         RedirectAttributes redirectAttrs) {
        try {
            // 로그인 사용자 정보 가져오기
            CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();

            // 삭제할 직원 객체 생성 (소프트 삭제 처리용)
            Employee emp = new Employee();
            emp.setEmpId(id);
            emp.setModUsId(user.getUserId());      // 로그인한 사용자 ID
            emp.setModIp(request.getRemoteAddr()); // 요청 IP

            service.delete(emp);
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("errorMessage", "직원 삭제 중 오류가 발생했습니다.");
        }
        return "redirect:/employees";
    }
}
