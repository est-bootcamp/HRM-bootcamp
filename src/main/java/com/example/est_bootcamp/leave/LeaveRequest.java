package com.example.est_bootcamp.leave;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LeaveRequest {

    private Long lvId;              // 휴가신청아이디
    private Long leaveTypeCode;     // 휴가유형 (lv_ty)

    private Long appEmpId;          // 승인자아이디
    private Long rqsEmpId;          // 신청자아이디

    private LocalDate startDate;    // 휴가 시작일
    private LocalDate endDate;      // 휴가 종료일

    private LeaveStatus status;     // 신청/승인 상태 (Enum)

    private LocalDateTime requestDate; // 신청일시

    // 등록/수정 공통
    private String regIp;
    private LocalDateTime regDate;
    private Long regUsId;

    private String modIp;
    private LocalDateTime modDate;
    private Long modUsId;

    private String useYn;       // 사용 여부 (Y/N)
    private String note;        // 비고

    // 2025-09-30 박상현 추가
    private String rqsName;       //
    private String appName;       //

}


