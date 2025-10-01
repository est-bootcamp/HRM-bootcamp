package com.example.est_bootcamp.repo;

import com.example.est_bootcamp.user.UserAccount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper // MyBatis 매퍼 인터페이스임을 명시
public interface UserAccountMapper {

    /**
     * 전체 사용자 조회
     * @return 모든 사용자 계정 리스트
     */
    List<UserAccount> findAll();

    /**
     * 단건 조회 (PK 기반)
     * @param usNo 사용자 고유 번호 (PK)
     * @return Optional<UserAccount>
     */
    Optional<UserAccount> findById(@Param("usNo") Long usNo);

    /**
     * 단건 조회 (loginId 기반)
     * @param loginId 로그인 아이디
     * @return Optional<UserAccount>
     */
    Optional<UserAccount> findByLoginId(@Param("loginId") String loginId);

    /**
     * 사용자 등록
     * @param user 저장할 사용자 엔티티
     */
    void insert(UserAccount user);

    /**
     * 사용자 수정
     * @param user 수정할 사용자 엔티티
     */
    void update(UserAccount user);

    /**
     * 사용자 삭제
     * @param usNo 삭제할 사용자 번호 (PK)
     */
    void delete(@Param("usNo") Long usNo);

    /**
     * 로그인 아이디로 사용자 조회 (Employee 조인 포함)
     * - UserAccount + Employee 엔티티를 함께 조회
     * - 로그인 및 권한 부여 시 Employee 정보까지 필요할 때 사용
     *
     * @param loginId 로그인 아이디
     * @return Optional<UserAccount> (내부에 Employee 포함)
     */
    Optional<UserAccount> findWithEmployeeByLoginId(@Param("loginId") String loginId);

}
