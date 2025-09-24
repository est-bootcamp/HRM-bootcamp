package com.example.est_bootcamp.repo;

import com.example.est_bootcamp.emp.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface EmployeeMapper {
    Optional<Employee> findById(@Param("empId") Long empId);
    List<Employee> findAll();
    Optional<Employee> findByUserNo(@Param("usNo") Long usNo);

    void insert(Employee employee);
    void update(Employee employee);
    void delete(@Param("empId") Long empId);
}
