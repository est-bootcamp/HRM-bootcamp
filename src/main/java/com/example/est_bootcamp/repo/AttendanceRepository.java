package com.example.est_bootcamp.repo;

import com.example.est_bootcamp.att.Attendance;
import com.example.est_bootcamp.core.BaseRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface AttendanceRepository extends BaseRepository<Attendance, Long> {

    // Employee 엔티티와 WorkDate 기준으로 조회
    Optional<Attendance> findByEmployeeEmpIdAndWorkDate(Long empId, LocalDate workDate);
}
