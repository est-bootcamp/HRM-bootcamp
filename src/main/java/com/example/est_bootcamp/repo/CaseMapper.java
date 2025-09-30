package com.example.est_bootcamp.repo;

import com.example.est_bootcamp.dto.Case;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CaseMapper {
    // CREATE
    void insertCase(Case cs);

    // READ
    List<Case> caseFindAll();
    Case findById(Long id);

    // UPDATE
    void updateCase(Case cs);

    // DELETE
    void deleteCase(Long id);
}