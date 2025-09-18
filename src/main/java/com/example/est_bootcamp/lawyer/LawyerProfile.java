package com.example.est_bootcamp.lawyer;

import com.example.est_bootcamp.emp.Employee;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="PR_LW")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class LawyerProfile {
    @Id
    @Column(name="emp_id")
    private Long id; // EMP FK와 동일 PK

    @MapsId
    @OneToOne(fetch = FetchType.LAZY) @JoinColumn(name="emp_id")
    private Employee employee;

    @Column(length=255) private String background;   // 출신(검사/판사/경찰 등)
    @Column(length=100) private String education;    // 학력
    @Column(length=50)  private String licenseNo;    // 변호사등록번호
    @Column(length=40)  private String practiceId;   // 업무분야 코드 (민사/형사 등)
}