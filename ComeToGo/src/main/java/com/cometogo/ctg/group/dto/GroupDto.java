package com.cometogo.ctg.group.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupDto {


    private Long groupId; // ctg_group 테이블의 PK. 동호회 생성 후 자동 생성되어 주입될 예정

    private Long ownerUserId; // 동호회 개설자 ID

    private Integer categoryId; // 동호회 카테고리 ID

    @NotBlank(message = "동호회 소개는 필수입니다.")
    @Size(min = 10, max = 200, message = "동호회 소개는 10자 이상 200자 이하여야 합니다.")
    private String intro;

    @NotBlank(message = "동호회 이름은 필수입니다.")
    @Size(min = 2, max = 50, message = "동호회 이름은 2자 이상 50자 이하여야 합니다.")
    private String groupName;

    @NotBlank(message = "가입 메시지는 필수입니다")
    @Size(min = 5, max = 200, message = "가입 메시지는 5자 이상 200자 이하여야 합니다.")
    private String joinMessage;

    // 동호회 위치 정보 (생성 시 함께 입력받음)
    @NotBlank(message = "도시는 필수입니다.")
    private String city; // ctg_group_location.city
    @NotBlank(message = "구/군은 필수입니다.")
    private String district; // ctg_group_location.district
    @NotBlank(message = "동/읍/면은 필수입니다.")
    private String neighborhood; // ctg_group_location.neighborhood

    private String profileImage; // ctg_group.profile_image

    private Timestamp createdAt; // 생성 일자

    private Integer groupStatus; // 동호회 상태 (예: 활성, 비활성)
}
