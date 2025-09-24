package com.example.est_bootcamp.lawyer;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PartnerLawyer {

    private Long empId;          // 임직원아이디 (EMP FK = PK)

    private String background;   // 이력/경력
    private String education;    // 학력
    private String licenseNo;    // 변호사등록번호
    private String practiceId;   // 업무분야

    private String regIp;        // 최초등록아이피
    private LocalDate regDate;   // 생성일시
    private Long regUsId;        // 최초등록사용자

    private String modIp;        // 수정시등록아이피
    private LocalDate modDate;   // 최종수정일시
    private Long modUsId;        // 최종수정사용자

    private String useYn;        // 사용 여부 (Y/N)
    private String note;         // 비고
}
