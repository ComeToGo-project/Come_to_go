package com.cometogo.ctg.admin.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserAdminDto {

    private Long userId;
    private String nickname;
    private String email;
    private String joinDate;
    private String userRole;
    private String userStatus;
    private LocalDateTime banStart;
    private LocalDateTime banEnd;
    private Long banDaysLeft;
}
