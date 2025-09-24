package com.example.est_bootcamp.repo;

import com.example.est_bootcamp.lawyer.PartnerLawyer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface PartnerLawyerMapper {

    Optional<PartnerLawyer> findById(@Param("empId") Long empId);

    List<PartnerLawyer> findAll();

    void insert(PartnerLawyer lawyer);

    void update(PartnerLawyer lawyer);

    void delete(@Param("empId") Long empId);
}
