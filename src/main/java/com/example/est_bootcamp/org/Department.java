package com.example.est_bootcamp.org;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "DPR")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Department {
    @Id
    @Column(name = "dpr_id")
    private Long id;

    @Column(name = "dpr_name", nullable = false, length = 100)
    private String name;

    @Column(name = "dpr_code", nullable = false, length = 20)
    private String code;

    @Column(name = "use_yn", nullable = false, length = 1)
    private String useYn;
}