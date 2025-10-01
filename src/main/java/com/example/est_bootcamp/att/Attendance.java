package com.example.est_bootcamp.att;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Attendance {

    private Long attId;        // 근태아이디
    private Long empId;        // 임직원아이디
    private LocalDate workDate;  // 근무일자
    private LocalDateTime checkIn;   // 출근시간
    private LocalDateTime checkOut;  // 퇴근시간
    private Long assignId;     // 사건배정아이디

    private String regIp;      // 최초등록아이피
    private LocalDateTime regDate; // 생성일시
    private Long regUsId;      // 최초등록사용자
    private String modIp;      // 수정시등록아이피
    private LocalDateTime modDate; // 최종수정일시
    private Long modUsId;      // 최종수정사용자
    private String useYn;      // Y/N
    private String note;       // 비고

    private String empName; // 직원 이름 JOIN용
}
