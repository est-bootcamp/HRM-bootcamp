package com.example.est_bootcamp.leave;


public enum LeaveStatus {REQUESTED, APPROVED, REJECTED, CANCELED, PENDING}
 // 휴가 신청의 처리 단계(요청,승인,거절,취소,보류) - LeaveRequest 클래스에서 status 필드의 타입으로 사용중
 // 유효하지 않은 값 입력 방지를 위해 Enum 사용