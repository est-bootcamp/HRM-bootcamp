package com.example.est_bootcamp.leave;

import com.example.est_bootcamp.emp.Employee;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="LV_RQ")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class LeaveRequest {
    @Id
    @Column(name="lv_id")
    private Long id;

    @Column(name="lv_ty", nullable=false)
    private Long leaveTypeCode; // LV_RQ_CD.lv_ty FK (단순화: 코드 값만)

    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="app_emp_id", nullable=false)
    private Employee approver; // 승인자

    @ManyToOne(fetch=FetchType.LAZY) @JoinColumn(name="rqs_emp_id", nullable=false)
    private Employee requester; // 신청자

    @Column(name="start_date", nullable=false)
    private LocalDate startDate;

    @Column(name="end_date", nullable=false)
    private LocalDate endDate;

    @Enumerated(EnumType.STRING)
    @Column(length=20, nullable=false)
    private LeaveStatus status; // REQUESTED/APPROVED/REJECTED/CANCELED

    @Column(name="request_date", nullable=false)
    private LocalDateTime requestDate;
}
