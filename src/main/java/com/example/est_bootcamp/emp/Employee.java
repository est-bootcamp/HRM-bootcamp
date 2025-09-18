package com.example.est_bootcamp.emp;

import com.example.est_bootcamp.common.Role;
import com.example.est_bootcamp.org.Department;
import com.example.est_bootcamp.org.Position;
import com.example.est_bootcamp.user.UserAccount;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name="EMP")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Employee {
    @Id
    @Column(name="emp_id")
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="us_no")
    private UserAccount userAccount;

    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="dpr_id", nullable=false)
    private Department department;

    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="pst_id", nullable=false)
    private Position position;

    @Column(nullable=false, length=100)
    private String name;

    @Column(nullable=false, length=100)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(length=20, nullable=false)
    private Role role; // ADMIN/OWNER/PARTNER/STAFF

    private LocalDate hireDate;
    private LocalDate resignDate;

    @Column(length=1, nullable=false)
    private String gender;

    @Column(name="use_yn", length=1, nullable=false)
    private String useYn;
}