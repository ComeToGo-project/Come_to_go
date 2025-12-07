package com.cometogo.ctg.admin.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SystemAdminDto {
    private Long categoryId;
    private Long parentId;

    @NotBlank(message = "카테고리명을 입력하세요.")
    private String categoryName;
}
