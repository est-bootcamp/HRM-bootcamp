package com.example.est_bootcamp.service;

import com.example.est_bootcamp.core.BaseService;
import com.example.est_bootcamp.emp.Employee;
import com.example.est_bootcamp.repo.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor

public class EmployeeService extends BaseService<Employee, Long> {
    private final EmployeeRepository repo;

    @Override
    protected JpaRepository<Employee, Long> getRepository(){
        return repo;
    }
}
