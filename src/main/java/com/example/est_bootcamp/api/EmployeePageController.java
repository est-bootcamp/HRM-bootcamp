package com.example.est_bootcamp.api;

import com.example.est_bootcamp.emp.Employee;
import com.example.est_bootcamp.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeePageController {

    private final EmployeeService service;

    /**
     * 직원 목록 화면
     */
    @GetMapping
    public String list(Model model) {
        model.addAttribute("employees", service.getAll());
        return "pages/list";
    }

    /**
     * 직원 등록 폼
     */
    @GetMapping("/new")
    public String createForm(Model model) {
        model.addAttribute("employee", new Employee());
        return "pages/form";
    }

    /**
     * 직원 수정 폼
     */
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("employee", service.getById(id));
        return "pages/form";
    }

    /**
     * 직원 저장 (등록/수정 공용)
     */
    @PostMapping
    public String save(@ModelAttribute Employee employee) {
        if (employee.getEmpId() == null) {
            service.create(employee); // 신규 등록
        } else {
            service.update(employee); // 수정
        }
        return "redirect:/employees";
    }

    /**
     * 직원 삭제
     */
    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/employees";
    }
}
