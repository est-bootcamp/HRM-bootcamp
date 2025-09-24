package com.example.est_bootcamp.user;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserAccount {
    private Long usNo;        // 회원번호 (PK)
    private String loginId;   // 로그인 ID
    private String password;  // 비밀번호
    private String regIp;     // 최초등록아이피
    private String regDate;   // 생성일시
    private Long regUsId;     // 최초등록사용자
    private String modIp;     // 수정시등록아이피
    private String modDate;   // 최종수정일시
    private Long modUsId;     // 최종수정사용자
    private String useYn;     // 사용여부 (Y/N)
    private String note;      // 비고
    private String usRole;    // 사용자 권한

    // ✅ UserDetails isEnabled() 대응
    public boolean isEnabled() {
        return "Y".equalsIgnoreCase(this.useYn);
    }
}
