package com.cometogo.ctg.group.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class MyGroupDto {

    @NotBlank
    private Long userId;
    @NotBlank
    private Long groupId;

    private String role;
    private String status;

    private Timestamp joinedAt;
    private Timestamp leftAt;
}
