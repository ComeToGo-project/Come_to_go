package com.cometogo.ctg.support.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReportDto {
    private Long reportId;
    private Long reporterId;
    private Long targetId;
    private String targetName;
    private String reportType;
    private String reason;
    private String contents;
    private String reportStatus;
    private LocalDateTime createdAt;
    private String email;
}
