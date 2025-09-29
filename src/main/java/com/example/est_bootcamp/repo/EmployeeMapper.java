package com.example.est_bootcamp.repo;

import com.example.est_bootcamp.emp.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface EmployeeMapper {
    Optional<Employee> findById(@Param("empId") Long empId);

    // ✅ 전체 조회 (페이징 없이)
    List<Employee> findAll();

    // 🔽 검색 + 페이징 조회
    List<Employee> findAllPaged(@Param("keyword") String keyword,
                                @Param("offset") int offset,
                                @Param("limit") int limit);

    // 🔽 전체 건수 (검색 포함)
    int count(@Param("keyword") String keyword);

    Optional<Employee> findByUserNo(@Param("usNo") Long usNo);

    void insert(Employee employee);
    void update(Employee employee);
    void delete(@Param("empId") Long empId);
}
