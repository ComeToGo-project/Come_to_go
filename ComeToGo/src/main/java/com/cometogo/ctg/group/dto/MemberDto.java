package com.cometogo.ctg.group.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data // getter, setter, equals, hashCode, toString 자동 생성
@NoArgsConstructor // 기본 생성자 자동 생성
@AllArgsConstructor // 모든 필드를 인자로 받는 생성자 자동 생성
public class MemberDto {


    private Long groupId;
    private Long userId;      // group_users 테이블의 user_id
    private String nickname;  // ctg_users 테이블의 nickname 추가
    private String role;      // 멤버 역할 (OWNER, MEMBER)
    private String status;    // 멤버 상태 (ACTIVE, BLOCKED)
    private Timestamp joinedAt; // 가입일

}

