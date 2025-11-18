package com.cometogo.ctg.admin.dto;

import lombok.Getter;

@Getter
public class GroupAdminDto {
    private Long groupId;
    private String groupName;
    private String ownerUserId;
    private String ownerNickname;
    private String categoryId;
    private String categoryName;
    private String city;
    private String createdAt;
    private String groupStatus;
}
