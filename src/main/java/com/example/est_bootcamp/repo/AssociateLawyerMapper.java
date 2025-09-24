package com.example.est_bootcamp.repo;

import com.example.est_bootcamp.lawyer.AssociateLawyer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface AssociateLawyerMapper {

    Optional<AssociateLawyer> findById(@Param("empId") Long empId);

    List<AssociateLawyer> findAll();

    void insert(AssociateLawyer lawyer);

    void update(AssociateLawyer lawyer);

    void delete(@Param("empId") Long empId);
}
