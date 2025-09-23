package com.example.est_bootcamp.user;

import com.example.est_bootcamp.emp.Employee;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "US")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class UserAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "us_no")
    private Long id;

    @Column(name = "login_id", nullable = false, unique = true, length = 50)
    private String loginId;

    @Column(name = "password", nullable = false, length = 255)
    private String password;  // BCrypt 암호화된 비밀번호

    @Column(name = "enabled", nullable = false)
    private boolean enabled = true;

    @CreatedDate
    @Column(name = "reg_date", updatable = false)
    @Comment("등록일")
    private LocalDateTime regDate;

    @LastModifiedDate
    @Column(name = "mod_date")
    @Comment("최종 수정일")
    private LocalDateTime modDate;

    // ✅ Employee 와 1:1 양방향 매핑
    @OneToOne(mappedBy = "userAccount", fetch = FetchType.LAZY)
    private Employee employee;
}
