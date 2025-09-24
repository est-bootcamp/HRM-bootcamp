package com.example.est_bootcamp.lawyer;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class BaseLawyerProfile {

    private Long empId;          // EMP FK = PK
    private String background;   // 이력/경력
    private String education;    // 학력
    private String licenseNo;    // 변호사등록번호
    private String practiceId;   // 업무분야

    private LocalDateTime createdAt; // 생성일시
    private LocalDateTime updatedAt; // 수정일시
}
