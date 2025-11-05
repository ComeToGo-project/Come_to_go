package com.cometogo.ctg.admin.dto;

import lombok.Getter;

@Getter
public class UserAdminDto {

    private Long userId;
    private String nickname;
    private String email;
    private String joinDate;
    private String userRole;
    private String userStatus;
}
