package com.example.est_bootcamp.service;

import com.example.est_bootcamp.org.Department;
import com.example.est_bootcamp.repo.DepartmentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentMapper departmentMapper;

    public List<Department> getAll() {
        return departmentMapper.findAll();
    }
}
