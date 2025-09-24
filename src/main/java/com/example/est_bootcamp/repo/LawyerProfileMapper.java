package com.example.est_bootcamp.repo;

import com.example.est_bootcamp.lawyer.BaseLawyerProfile;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface LawyerProfileMapper {

    Optional<BaseLawyerProfile> findById(@Param("empId") Long empId);

    List<BaseLawyerProfile> findAll();

    void insert(BaseLawyerProfile lawyer);

    void update(BaseLawyerProfile lawyer);

    void delete(@Param("empId") Long empId);
}
