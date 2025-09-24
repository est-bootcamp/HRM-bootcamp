package com.example.est_bootcamp.api;

import com.example.est_bootcamp.emp.Employee;
import com.example.est_bootcamp.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService service;

    /**
     * 전체 직원 조회
     */
    @GetMapping
    public List<Employee> getAll() {
        return service.getAll();
    }

    /**
     * 직원 단건 조회
     */
    @GetMapping("/{id}")
    public Employee getById(@PathVariable Long id) {
        return service.getById(id);
    }

    /**
     * 직원 등록
     */
    @PostMapping
    public void create(@RequestBody Employee employee) {
        service.create(employee);
    }

    /**
     * 직원 수정
     */
    @PutMapping("/{id}")
    public void update(@PathVariable Long id, @RequestBody Employee employee) {
        employee.setEmpId(id); // 요청 body와 PathVariable id 동기화
        service.update(employee);
    }

    /**
     * 직원 삭제
     */
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
