package com.cometogo.ctg.support.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReportImageDto {
    private Long imageId;
    private Long reportId;
    private String filePath;
    private String originalName;
    private Long fileSize;
    private LocalDateTime uploadedAt;
}
