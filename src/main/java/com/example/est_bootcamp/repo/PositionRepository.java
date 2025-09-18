package com.example.est_bootcamp.repo;

import com.example.est_bootcamp.core.BaseRepository;
import com.example.est_bootcamp.org.Position;
import org.springframework.stereotype.Repository;

@Repository
public interface PositionRepository extends BaseRepository<Position, Long> {
}