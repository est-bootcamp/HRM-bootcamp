package com.example.est_bootcamp.repo;

import com.example.est_bootcamp.leave.LeaveRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface LeaveRequestMapper {

    Optional<LeaveRequest> findById(@Param("lvId") Long lvId);

    List<LeaveRequest> findAll();

    void insert(LeaveRequest leave);

    void update(LeaveRequest leave);

    void delete(@Param("lvId") Long lvId);
}
