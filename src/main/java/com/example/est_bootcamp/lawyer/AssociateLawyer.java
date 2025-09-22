package com.example.est_bootcamp.lawyer;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "AS_LW")
@Getter
@Setter
@NoArgsConstructor
@Builder
public class AssociateLawyer extends BaseLawyerProfile {
    // 어쏘 변호사 전용 속성이 있다면 여기에 추가
}