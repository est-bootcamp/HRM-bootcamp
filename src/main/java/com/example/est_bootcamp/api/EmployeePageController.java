package com.example.est_bootcamp.api;

import com.example.est_bootcamp.emp.Employee;
import com.example.est_bootcamp.service.EmployeeService;
import com.example.est_bootcamp.service.DepartmentService;
import com.example.est_bootcamp.service.PositionService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
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

    /** 직원 목록 화면 */
    @GetMapping
    public String list(Model model) {
        model.addAttribute("employees", service.getAll());
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
    @GetMapping("/{id}/delete")
    @PreAuthorize("hasRole('ADMIN')")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttrs) {
        try {
            service.delete(id);
        } catch (Exception e) {
            redirectAttrs.addFlashAttribute("errorMessage", "직원 삭제 중 오류가 발생했습니다.");
        }
        return "redirect:/employees";
    }
}
