package com.example.est_bootcamp.repo;

import com.example.est_bootcamp.user.UserAccount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Optional;

@Mapper
public interface UserAccountMapper {

    Optional<UserAccount> findByLoginId(@Param("loginId") String loginId);

    Optional<UserAccount> findById(@Param("usNo") Long usNo);

    void insert(UserAccount user);

    void update(UserAccount user);

    void delete(@Param("usNo") Long usNo);
}
