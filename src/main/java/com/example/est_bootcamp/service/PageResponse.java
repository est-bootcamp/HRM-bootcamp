package com.example.est_bootcamp.service;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * MyBatis 기반 페이지네이션 응답 DTO (1-based page index)
 */
@Data
@AllArgsConstructor
public class PageResponse<T> {
    private List<T> content;   // 현재 페이지 데이터
    private int page;          // 현재 페이지 번호 (1부터 시작)
    private int size;          // 페이지 크기
    private int totalElements; // 전체 데이터 건수

    /** 전체 페이지 수 */
    public int getTotalPages() {
        return (int) Math.ceil((double) totalElements / size);
    }

    /** 첫 페이지 여부 */
    public boolean isFirst() {
        return page == 1;  // ✅ 1이면 첫 페이지
    }

    /** 마지막 페이지 여부 */
    public boolean isLast() {
        return page >= getTotalPages(); // ✅ 마지막 페이지 체크
    }
}
