package com.example.est_bootcamp.core;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

/**
 * 모든 엔티티 Repository가 공통적으로 상속받는 추상 레포지토리
 * 여기서 공통 메서드를 정의하면 전체 엔티티에서 재사용 가능
 */
@NoRepositoryBean
public interface BaseRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {
    // 예시) 모든 Repository에서 공통으로 쓸 메서드 추가 가능
    // Optional<T> findByName(String name);
}