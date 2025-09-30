package com.example.est_bootcamp.service;

import com.example.est_bootcamp.emp.Employee;
import com.example.est_bootcamp.repo.EmployeeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {
    private final EmployeeMapper mapper;

    public Employee getById(Long empId) {
        return mapper.findById(empId).orElseThrow();
    }

    public List<Employee> getAll() {
        return mapper.findAll();
    }

    public void create(Employee emp) {
        mapper.insert(emp);
    }

    public void update(Employee emp) {
        mapper.update(emp);
    }

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
