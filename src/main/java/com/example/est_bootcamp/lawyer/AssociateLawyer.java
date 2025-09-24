package com.example.est_bootcamp.lawyer;

import lombok.*;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AssociateLawyer {

    private Long empId;         // 임직원 아이디 (PK, EMP FK)

    private String background;  // 이력/경력
    private String education;   // 학력
    private String licenseNo;   // 변호사등록번호
    private String practiceId;  // 업무분야

    private String regIp;       // 최초 등록 아이피
    private LocalDate regDate;  // 생성일시
    private Long regUsId;       // 최초 등록 사용자

    private String modIp;       // 수정 시 등록 아이피
    private LocalDate modDate;  // 최종 수정일시
    private Long modUsId;       // 최종 수정 사용자

    private String useYn;       // Y/N
    private String note;        // 비고
}
