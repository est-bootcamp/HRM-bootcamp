package com.example.est_bootcamp.emp;

import com.example.est_bootcamp.common.Role;
import com.example.est_bootcamp.org.Department;
import com.example.est_bootcamp.org.Position;
import com.example.est_bootcamp.user.UserAccount;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "EMP")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // PK 자동 증가라면 추가
    @Column(name = "emp_id")
    private Long empId;

    // 사용자(FK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "us_no", unique = true)
    private UserAccount userAccount;

    // 부서(FK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dpr_id", nullable = false)
    private Department department;

    // 직급(FK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pst_id", nullable = false)
    private Position position;

    // 직원명
    @Column(name = "emp_name", nullable = false, length = 100)
    private String empName;

    // 이메일
    @Column(nullable = false, length = 100)
    private String email;

    // 역할
    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private Role role; // ADMIN / OWNER / PARTNER / STAFF

    // 생년월일
    @Column(name = "birth_date")
    private LocalDate birthDate;

    // 전화번호
    @Column(name = "phone_no", length = 20)
    private String phoneNo;

    // 입사일 / 퇴사일
    @Column(name = "hire_date")
    private LocalDate hireDate;

    @Column(name = "resign_date")
    private LocalDate resignDate;

    // 성별
    @Column(length = 1, nullable = false)
    private String gender;

    // 사용 여부
    @Column(name = "use_yn", length = 1, nullable = false)
    private String useYn;

}
