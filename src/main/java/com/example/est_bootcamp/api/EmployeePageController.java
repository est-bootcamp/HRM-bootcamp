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

    /** 직원 목록 (페이지네이션 + 검색) */
    @GetMapping
    public String list(@RequestParam(defaultValue = "1") int page,
                       @RequestParam(defaultValue = "10") int size,
                       @RequestParam(defaultValue = "") String keyword,
                       Model model) {

        // 서비스에서 페이징 처리된 결과 가져오기
        PageResponse<Employee> employeePage = service.getEmpLstAllPaged(page, size, keyword);

        // 모델에 담기
        model.addAttribute("employeePage", employeePage);
        model.addAttribute("keyword", keyword);

        return "list";
    }

    /** 직원 등록 폼 */
    @GetMapping("/new")
    @PreAuthorize("hasRole('ADMIN')")
    public String createForm(Model model) {
        model.addAttribute("employee", new Employee());
        model.addAttribute("departments", deptService.getAll());
        model.addAttribute("positions", positionService.getAll());
        return "form";
    }

    /** 직원 수정 폼 */
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

    /** 직원 저장 (등록/수정 공용) */
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

        if (employee.getEmpId() == null) {
            service.create(employee);
        } else {
            service.update(employee);
        }
        return "redirect:/employees";
    }

    /** 직원 삭제 */
    @PostMapping("/{id}/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public String delete(@PathVariable Long id,
                         HttpServletRequest request,
                         Authentication authentication,
                         RedirectAttributes redirectAttrs) {
        try {
            // 로그인 사용자
            CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();

            // 삭제할 직원 객체 생성
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
