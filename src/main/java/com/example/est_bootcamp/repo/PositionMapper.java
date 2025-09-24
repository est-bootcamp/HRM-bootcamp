package com.example.est_bootcamp.repo;

import com.example.est_bootcamp.org.Position;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface PositionMapper {

    Optional<Position> findById(@Param("pstId") Long pstId);

    List<Position> findAll();

    void insert(Position position);

    void update(Position position);

    void delete(@Param("pstId") Long pstId);
}
