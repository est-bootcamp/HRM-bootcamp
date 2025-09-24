package com.example.est_bootcamp.repo;

import com.example.est_bootcamp.org.Department;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface DepartmentMapper {
    Optional<Department> findById(Long id);
    List<Department> findAll();

    void insert(Department dept);
    void update(Department dept);
    void delete(Long id);
}
