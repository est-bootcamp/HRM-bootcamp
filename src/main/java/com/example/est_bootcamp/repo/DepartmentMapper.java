package com.example.est_bootcamp.repo;

import com.example.est_bootcamp.org.Department;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper // MyBatis 매퍼 인터페이스임을 명시 (스프링에서 자동으로 빈 등록됨)
public interface DepartmentMapper {

    /**
     * 부서 단건 조회
     * @param id 부서 고유 ID (PK)
     * @return Optional<Department> (존재하지 않을 경우 빈 Optional 반환)
     */
    Optional<Department> findById(Long id);

    /**
     * 전체 부서 목록 조회
     * @return List<Department>
     */
    List<Department> findAll();

    /**
     * 부서 등록
     * @param dept 저장할 부서 엔티티
     */
    void insert(Department dept);

    /**
     * 부서 수정
     * @param dept 수정할 부서 엔티티
     */
    void update(Department dept);

    /**
     * 부서 삭제
     * @param id 삭제할 부서 고유 ID (PK)
     */
    void delete(Long id);
}
