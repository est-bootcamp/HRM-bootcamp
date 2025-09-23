package com.example.est_bootcamp.repo;

import com.example.est_bootcamp.core.BaseRepository;
import com.example.est_bootcamp.emp.Employee;
import com.example.est_bootcamp.user.UserAccount;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends BaseRepository<Employee, Long> {
    Optional<Employee> findByUserAccount(UserAccount user);
}