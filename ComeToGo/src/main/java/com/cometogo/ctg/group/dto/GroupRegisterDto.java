package com.cometogo.ctg.group.dto;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class GroupRegisterDto {

    private Long groupJoinId;
    private Long userId;
    private Long groupId;
    private String message;
    private String joinStatus;
    private Timestamp createdAt;
    private Timestamp processedAt;
    private Long processedBy;
}
