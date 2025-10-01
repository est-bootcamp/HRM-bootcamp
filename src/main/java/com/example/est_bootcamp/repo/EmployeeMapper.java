package com.example.est_bootcamp.repo;

import com.example.est_bootcamp.emp.Employee;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

/**
 * EmployeeMapper
 * - MyBatis Mapper 인터페이스
 * - 직원(Employee) 테이블 관련 SQL을 정의하고 XML 매퍼와 매핑됨
 */
@Mapper
public interface EmployeeMapper {
    /**
     * 직원 단건 조회
     */
    Optional<Employee> findById(@Param("empId") Long empId);

    /**
     * 직원 전체 조회 (페이징 없음)
     * - use_yn = 'Y' 조건이 걸려 있을 수 있음 (XML 확인 필요)
     */
    List<Employee> findAll();

/**
 * 직원 목록 조회 (검색 + 페이징 지원)
 * - keyword가 있으면 이름, 이메일, 전화번호에 LIKE 검색
 * - offset, limit 기반 페이징 처리
 */
 List<Employee> findAllPaged(@Param("keyword") String keyword,
                                @Param("offset") int offset,
                                @Param("limit") int limit);

    /**
     * 직원 수 카운트 (검색 조건 포함)
     * - 페이징 전체 페이지 계산 시 사용
     */
    int count(@Param("keyword") String keyword);

    /**
     * 사용자 번호(usNo)로 직원 조회
     */
    Optional<Employee> findByUserNo(@Param("usNo") Long usNo);

    /**
     * 직원 등록 (INSERT)
     * - PK는 DB에서 자동 생성 (useGeneratedKeys)
     */
    void insert(Employee employee);

    /**
     * 직원 수정 (UPDATE)
     * - empId 기준으로 갱신
     */
    void update(Employee employee);

    /**
     * 직원 삭제 (소프트 삭제 처리)
     * - 실제 삭제가 아닌 use_yn = 'N' 으로 변경
     */
    void delete(Employee employee);
}
