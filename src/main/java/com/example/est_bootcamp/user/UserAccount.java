package com.example.est_bootcamp.user;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import java.time.LocalDateTime;

@Entity
@Table(name="US")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class UserAccount {
    @Id
    @Column(name="us_no")
    private Long id;

    @Column(name="login_id", nullable=false, unique=true, length=50)
    private String loginId;

    @Column(name="password", nullable=false, length=255)
    private String passwordHash;

    @Column(name="use_yn", nullable=false, length=1)
    private String useYn;

    @Comment("감사/감시용")
    private LocalDateTime regDate;
}