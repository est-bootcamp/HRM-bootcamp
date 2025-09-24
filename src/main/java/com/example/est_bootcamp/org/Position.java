package com.example.est_bootcamp.org;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Position {
    private Long pstId;      // 직급아이디 (PK)
    private String pstCode;  // 직급코드
    private String pstName;  // 직급명
    private String pstDscr;  // 직급 상세 설명
    private Integer level;   // 직급 레벨값
    private String useYn;    // 사용 여부 (Y/N)
}
