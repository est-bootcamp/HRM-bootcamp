package com.example.est_bootcamp.leave;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LeaveTypeCode {

    private Long leaveType;              // lv_ty (휴가유형ID)
    private String leaveTypeDes;          // lv_ty_de (휴가유형명)
    private String genRe;           // gen_re (성별제한)
    private Long mxDpm;             // mx_dpm (월간 최대 사용일수)
    private Long mxDpy;             // mx_dpy (연간 최대 사용일수)
    private String useYn;           // use_yn (사용여부)



}
