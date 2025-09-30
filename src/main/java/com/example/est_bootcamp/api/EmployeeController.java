package com.example.est_bootcamp.api;

import com.example.est_bootcamp.emp.Employee;
import com.example.est_bootcamp.security.CustomUserDetails;
import com.example.est_bootcamp.service.EmployeeService;
import com.example.est_bootcamp.service.PageResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService service;

    /**
     * 직원 목록 (검색 + 페이지네이션)
     * 예: GET /api/employees?page=1&size=10&keyword=홍길동
     */
    @GetMapping
    public PageResponse<Employee> getEmployees(
            @RequestParam(defaultValue = "1") int page,   // 1부터 시작
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String keyword) {

        return service.getEmpLstAllPaged(page, size, keyword);
    }

    /**
     * 직원 단건 조회
     */
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getById(@PathVariable Long id) {
        Employee employee = service.getById(id);
        return ResponseEntity.ok(employee);
    }

    /**
     * 직원 등록
     */
    @PostMapping
    public ResponseEntity<Void> create(@RequestBody Employee employee) {
        service.create(employee);
        // Location 헤더에 생성된 자원의 URI 포함
        URI location = URI.create("/api/employees/" + employee.getEmpId());
        return ResponseEntity.created(location).build();
    }

    /**
     * 직원 수정
     */
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody Employee employee) {
        employee.setEmpId(id);
        service.update(employee);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    /**
     * 직원 삭제
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       HttpServletRequest request,
                                       Authentication authentication) {

        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();

        Employee employee = new Employee();
        employee.setEmpId(id);
        employee.setModIp(request.getRemoteAddr());
        employee.setModUsId(user.getUserId());

        service.delete(employee);
        return ResponseEntity.noContent().build();
    }
}
