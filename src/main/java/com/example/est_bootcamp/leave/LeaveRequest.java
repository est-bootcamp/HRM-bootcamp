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

    private Long lvId;              // 휴가신청아이디 - 고유 식별 번호
    private Long leaveTypeCode;     // 휴가유형 고유번호 (lv_ty)
    private String leaveTypeName;   // 휴가유형명(lv_ty_de) - LV_RQ_CD 테이블에서 서브쿼리로 가져옴

    private Long appEmpId;          // 승인자아이디 - 신청한 직원의 상급자
    private Long rqsEmpId;          // 신청자아이디 - 휴가를 신청한 직원의 ID

    private LocalDate startDate;    // 휴가 시작일
    private LocalDate endDate;      // 휴가 종료일

    private LeaveStatus status;     // 신청 상태 (Enum)

    private LocalDateTime requestDate; // 신청일시

    // 등록/수정 공통
    private String regIp;           // 등록 시 접속 IP
    private LocalDateTime regDate;  // 등록 일시
    private Long regUsId;           // 등록 사용자 ID

    private String modIp;           // 수정 시 접속 IP
    private LocalDateTime modDate;  // 수정 일시
    private Long modUsId;           // 수정 사용자 ID

    private String useYn;       // 사용 여부 (Y/N) - 논리적 삭제 플래그
    private String note;        // 비고

    private String rqsName;       // 신청자 성명 - EMP 테이블을 서브쿼리로 조회하여 신청자 이름을 가져옴
    private String appName;       // 승인자 성명 - EMP 테이블을 서브쿼리로 조회하여 승인자 이름을 가져옴

}


