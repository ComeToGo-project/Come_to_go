package com.cometogo.ctg.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserAddressDto {
    private Long userId; // FK
    @NotBlank(message = "시/도 입력은 필수입니다.")
    private String city;
    @NotBlank(message = "구/군 입력은 필수입니다.")
    private String district;
    @NotBlank(message = "동/읍/면 입력은 필수입니다.")
    private String neighborhood;
}
