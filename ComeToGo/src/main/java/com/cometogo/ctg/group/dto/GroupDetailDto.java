package com.cometogo.ctg.group.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupDetailDto {
    // --- DB에서 조회하여 화면에 표시할 필드 ---
    private Long groupId;          // ctg_group.group_id
    private Long ownerUserId;      // ctg_group.owner_user_id
    private Long categoryId;       // ctg_group.category_id
    private String groupName;      // ctg_group.group_name
    private String intro;          // ctg_group.intro
    private String joinMessage;    // ctg_group.join_message
    private Timestamp createdAt;   // ctg_group.created_at
    private String profileImage;   // ctg_group.profile_image
    private String groupStatus;
    // --- 조인으로 가져오는 추가 정보 ---
    private String categoryName;   // ctg_categories.category_name
    private String region;         // ctg_group_location에서 city, district, neighborhood 조합
    private int memberCount;       // group_users 테이블에서 멤버 수
    private int postCount;         // group_board 테이블에서 게시글 수
    private String ownerNickname;  // 그룹장 닉네임

    // --- 로그인 사용자 관련 정보 (쿼리 CASE WHEN 결과) ---
    private boolean isMember;      // 로그인 사용자가 이 그룹의 멤버인지 여부
    private boolean isOwner;       // 로그인 사용자가 그룹장인지 여부

    // --- 서브 리스트 (Thymeleaf 반복문용) ---
    private List<MemberDto> members;
    private List<ScheduleDto> schedules;
    private List<PostDto> posts;


}
