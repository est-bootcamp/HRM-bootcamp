package com.example.est_bootcamp.service;

import com.example.est_bootcamp.emp.Employee;
import com.example.est_bootcamp.repo.EmployeeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 직원 관련 비즈니스 로직을 처리하는 Service 클래스
 * - DB 접근은 EmployeeMapper(MyBatis)에게 위임
 */
@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeMapper mapper;

    /**
     * 직원 단건 조회
     * @param empId 직원 PK
     * @return Employee (없으면 NoSuchElementException 발생)
     */
    public Employee getById(Long empId) {
        return mapper.findById(empId).orElseThrow();
    }

    /**
     * 직원 전체 조회 (페이징 없음)
     * - 보통 관리자용에서 전체 데이터가 필요할 때 사용
     */
    public List<Employee> getAll() {
        return mapper.findAll();
    }

    /**
     * 직원 등록
     * - 신규 직원 정보를 DB에 저장
     */
    public void create(Employee emp) {
        mapper.insert(emp);
    }

    /**
     * 직원 수정
     * - empId 기준으로 업데이트
     */
    public void update(Employee emp) {
        mapper.update(emp);
    }

    /**
     * 직원 삭제 (소프트 삭제 방식)
     * - 실제 DELETE가 아니라 use_yn = 'N'으로 변경
     */
    public void delete(Employee emp) {
        mapper.delete(emp);
    }

    /**
     * 검색 + 페이지네이션 지원 (1-based page index)
     */
    public PageResponse<Employee> getEmpLstAllPaged(int page, int size, String keyword) {
        // 페이지는 1부터 시작 → offset은 (page - 1) * size
        int offset = (page - 1) * size;

        List<Employee> employees = mapper.findAllPaged(keyword, offset, size);
        int total = mapper.count(keyword);

        return new PageResponse<>(employees, page, size, total);
    }
}
