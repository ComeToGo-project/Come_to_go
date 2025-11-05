package com.cometogo.ctg.user.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserVerifyDto {
    private Long verifyId;
    private String email;
    private String verifyCode;
    private Boolean isVerify;
    private LocalDateTime createdAt;
    private LocalDateTime expiredAt;
    private LocalDateTime verifiedAt;
}
