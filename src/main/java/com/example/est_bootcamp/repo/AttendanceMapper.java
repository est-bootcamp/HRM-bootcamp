package com.example.est_bootcamp.repo;

import com.example.est_bootcamp.att.Attendance;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Mapper
public interface AttendanceMapper {

    Optional<Attendance> findById(@Param("attId") Long attId);

    // 추가: 특정 직원의 특정 날짜 근태 조회
    Optional<Attendance> findByEmpAndDate(@Param("empId") Long empId,
                                          @Param("workDate") LocalDate workDate);

    void insert(Attendance attendance);

    void update(Attendance attendance);

    void delete(@Param("attId") Long attId);
}
