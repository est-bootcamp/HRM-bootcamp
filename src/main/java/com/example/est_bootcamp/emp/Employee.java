package com.example.est_bootcamp.emp;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Employee {

    private Long empId;      // PK
    private Long usNo;       // 회원번호
    private Long dprId;      // 부서 ID
    private Long pstId;      // 직급 ID

    private String name;     // 이름
    private String email;    // 이메일
    private String phone;    // 연락처
    private LocalDate hireDate;    // 입사일
    private LocalDate resignDate;  // 퇴사일
    private String status;   // 재직 상태
    private String address;  // 주소
    private String gender;   // 성별 (M/F)

    private String regIp;    // 최초 등록 아이피
    private LocalDateTime regDate;   // 생성일시
    private Long regUsId;    // 최초 등록 사용자

    private String modIp;    // 수정 시 등록 아이피
    private LocalDateTime modDate;   // 최종 수정일시
    private Long modUsId;    // 최종 수정 사용자

    private String useYn;    // Y/N
    private String note;     // 비고

}
