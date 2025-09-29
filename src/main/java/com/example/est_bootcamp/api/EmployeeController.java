package com.example.est_bootcamp.api;

import com.example.est_bootcamp.emp.Employee;
import com.example.est_bootcamp.service.EmployeeService;
import com.example.est_bootcamp.service.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService service;

    /**
     * ✅ 직원 목록 (검색 + 페이지네이션)
     * 예: GET /api/employees?page=1&size=10&keyword=홍길동
     */
    @GetMapping
    public PageResponse<Employee> getEmployees(
            @RequestParam(defaultValue = "1") int page,   // 1부터 시작
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String keyword) {

        // ✅ Service에서 페이징 처리된 데이터 가져오기
        return service.getAllPaged(page, size, keyword);
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
    public ResponseEntity<Long> create(@RequestBody Employee employee) {
        service.create(employee);
        return ResponseEntity.ok(employee.getEmpId()); // 생성된 PK 반환
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
