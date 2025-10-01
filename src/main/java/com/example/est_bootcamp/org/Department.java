package com.example.est_bootcamp.org;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Department {
    private Long dprId;      // 부서 ID
    private String dprCode;  // 부서 코드
    private String dprName;  // 부서 이름

    private String dprScope; // 부서 범위
    private String dprDscr;  // 부서 설명

    private String useYn;    // 사용 여부
}
