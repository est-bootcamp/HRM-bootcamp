package com.example.est_bootcamp.org;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="PST")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Position {
    @Id
    @Column(name="pst_id")
    private Long id;

    @Column(name="pst_code", nullable=false, length=20)
    private String code;

    @Column(name="pst_name", nullable=false, length=40)
    private String name;

    @Column(name="level")
    private Integer level;

    @Column(name="use_yn", nullable=false, length=1)
    private String useYn;
}