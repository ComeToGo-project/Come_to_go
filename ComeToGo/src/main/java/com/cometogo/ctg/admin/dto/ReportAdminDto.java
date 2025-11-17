package com.cometogo.ctg.admin.dto;

import lombok.Getter;

@Getter
public class ReportAdminDto {
    private Long reportId;
    private Long targetId;
    private String targetName;
    private String reportType;
    private String reason;
    private String createdAt;
    private String reportStatus;
}
