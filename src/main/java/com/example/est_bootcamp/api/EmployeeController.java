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
     * 직원 목록 조회 API (검색 + 페이지네이션 지원)
     * - GET /api/employees?page=1&size=10&keyword=홍길동
     * - 기본값: page=1, size=10, keyword=""
     * - 응답: PageResponse<Employee>
     */
    @GetMapping
    public PageResponse<Employee> getEmployees(
            @RequestParam(defaultValue = "1") int page,   // 1부터 시작
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String keyword) {

        return service.getEmpLstAllPaged(page, size, keyword);
    }

    /**
     * 직원 단건 조회 API
     * - GET /api/employees/{id}
     * - 응답: Employee JSON
     * - 예외: 해당 ID가 없으면 NoSuchElementException 발생 (ControllerAdvice로 처리 권장)
     */
    @GetMapping("/{id}")
    public ResponseEntity<Employee> getById(@PathVariable Long id) {
        Employee employee = service.getById(id);
        return ResponseEntity.ok(employee);
    }

    /**
     * 직원 등록 API
     * - POST /api/employees
     * - 요청 Body: Employee JSON
     * - 응답: 201 Created + Location 헤더에 생성된 직원 리소스 경로 반환
     */
    @PostMapping
    public ResponseEntity<Void> create(@RequestBody Employee employee) {
        service.create(employee);
        // Location 헤더에 생성된 자원의 URI 포함
        URI location = URI.create("/api/employees/" + employee.getEmpId());
        return ResponseEntity.created(location).build();
    }

    /**
     * 직원 수정 API
     * - PUT /api/employees/{id}
     * - 요청 Body: Employee JSON
     * - PathVariable {id} 값과 Body의 empId를 동기화
     * - 응답: 204 No Content (성공했지만 반환 데이터 없음)
     */
    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody Employee employee) {
        employee.setEmpId(id);
        service.update(employee);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    /**
     * 직원 삭제 API (소프트 삭제)
     * - DELETE /api/employees/{id}
     * - 요청자 정보(Authentication)와 IP(HttpServletRequest)를 함께 기록
     * - 실제 삭제가 아닌 useYn='N' 업데이트
     * - 응답: 204 No Content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id,
                                       HttpServletRequest request,
                                       Authentication authentication) {

        // 로그인 사용자 정보 가져오기
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();

        // 삭제 대상 Employee 생성 (소프트 삭제 기록용)
        Employee employee = new Employee();
        employee.setEmpId(id);
        employee.setModIp(request.getRemoteAddr()); // 요청자 IP
        employee.setModUsId(user.getUserId());      // 수정한 사용자 ID

        service.delete(employee);
        return ResponseEntity.noContent().build();  // 204 No Content
    }
}
