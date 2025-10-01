package com.example.est_bootcamp.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@Getter
@Setter
public class Case {
    private Long csId;          //사건ID
    private Long csTyId;        //사건 종류
    private String csName;      //사건명
    private String clientName;  //고객명
    private String csStatus;    //사건 상태
    private String regIp;       //
    private Date regDate;       //
    private Long regUsId;       //
    private String modIp;       //
    private Date modDate;       //
    private Long modUsId;       //
    private String useYn;       //
    private String note;        //
}
