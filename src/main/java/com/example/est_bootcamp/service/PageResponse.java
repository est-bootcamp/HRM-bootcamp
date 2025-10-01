package com.example.est_bootcamp.service;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * MyBatis 기반 페이지네이션 응답 DTO
 * - 페이지네이션 처리된 데이터를 담아 컨트롤러 → 뷰/클라이언트로 전달할 때 사용
 * - page 번호는 1부터 시작 (0-based 아님 주의!)
 */
@Data
@AllArgsConstructor
public class PageResponse<T> {
    private List<T> content;   // 현재 페이지 데이터
    private int page;          // 현재 페이지 번호 (1부터 시작)
    private int size;          // 페이지 크기
    private int totalElements; // 전체 데이터 건수

    /**
     * 전체 페이지 수 계산
     * @return 전체 페이지 개수 (totalElements / size 를 올림 처리)
     */
    public int getTotalPages() {
        return (int) Math.ceil((double) totalElements / size);
    }

    /**
     * 현재 페이지가 첫 페이지인지 여부
     * @return true → 첫 페이지(1), false → 그 외
     */
    public boolean isFirst() {
        return page == 1;  // ✅ 1이면 첫 페이지
    }

    /**
     * 현재 페이지가 마지막 페이지인지 여부
     * @return true → 마지막 페이지, false → 그 외
     */
    public boolean isLast() {
        return page >= getTotalPages(); // ✅ 마지막 페이지 체크
    }
}
