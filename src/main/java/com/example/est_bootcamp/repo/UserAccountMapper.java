package com.example.est_bootcamp.repo;

import com.example.est_bootcamp.user.UserAccount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface UserAccountMapper {

    // 전체 조회
    List<UserAccount> findAll();

    // 단건 조회 (PK)
    Optional<UserAccount> findById(@Param("usNo") Long usNo);

    // 단건 조회 (loginId)
    Optional<UserAccount> findByLoginId(@Param("loginId") String loginId);

    // 등록
    void insert(UserAccount user);

    // 수정
    void update(UserAccount user);

    // 삭제
    void delete(@Param("usNo") Long usNo);

    // ✅ Employee 조인 포함 조회
    Optional<UserAccount> findWithEmployeeByLoginId(@Param("loginId") String loginId);

}
