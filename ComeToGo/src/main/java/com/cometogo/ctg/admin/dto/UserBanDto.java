package com.cometogo.ctg.admin.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserBanDto {

    private Long banId;
    private Long userId;
    private LocalDateTime banStart;
    private LocalDateTime banEnd;
    private String banStatus;
}
