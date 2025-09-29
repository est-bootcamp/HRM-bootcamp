package com.example.est_bootcamp.service;

import com.example.est_bootcamp.org.Position;
import com.example.est_bootcamp.repo.PositionMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PositionService {

    private final PositionMapper positionMapper;

    public List<Position> getAll() {
        return positionMapper.findAll();
    }
}