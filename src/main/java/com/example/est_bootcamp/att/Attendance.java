package com.example.est_bootcamp.att;

import com.example.est_bootcamp.emp.Employee;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "ATT")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Attendance {

    @Id
    @Column(name = "att_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emp_id", nullable = false)
    private Employee employee;

    @Column(name = "work_date", nullable = false)
    private LocalDate workDate;

    private LocalDateTime checkIn;
    private LocalDateTime checkOut;

    @Column(length = 1, nullable = false)
    private String useYn;
}