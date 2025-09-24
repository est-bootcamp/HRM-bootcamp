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

    public void delete(Long empId) {
        mapper.delete(empId);
    }
}
