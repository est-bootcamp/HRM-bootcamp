package com.example.est_bootcamp.service;

import com.example.est_bootcamp.dto.Case;
import com.example.est_bootcamp.repo.CaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CaseService {

    @Autowired
    private CaseMapper caseMapper;

    public List<Case> getAllCases() {
        return caseMapper.caseFindAll();
    }

    public Case getCaseById(Long csId) {
        return caseMapper.findById(csId);
    }

    public void createCase(Case cs) {
        caseMapper.insertCase(cs);
    }

    public void updateCase(Case cs) {
        caseMapper.updateCase(cs);
    }

    //수정
    public void deleteCase(Long csId) {
        caseMapper.deleteCase(csId);
    }
}
