package com.example.est_bootcamp.org;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Department {
    private Long dprId;
    private String dprCode;
    private String dprName;
    private String dprScope; // 추가
    private String dprDscr;  // 추가
    private String useYn;
}
