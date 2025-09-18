package com.example.est_bootcamp.repo;

import com.example.est_bootcamp.core.BaseRepository;
import com.example.est_bootcamp.emp.Employee;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends BaseRepository<Employee, Long> {
}