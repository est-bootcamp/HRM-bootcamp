package com.example.est_bootcamp.lawyer;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "PR_LW")
@Getter
@Setter
@NoArgsConstructor
@Builder
public class PartnerLawyer extends BaseLawyerProfile {
    // 파트너 변호사 전용 속성이 있다면 여기에 추가
}