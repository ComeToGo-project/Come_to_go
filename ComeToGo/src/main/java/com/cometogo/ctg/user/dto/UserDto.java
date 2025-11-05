package com.cometogo.ctg.user.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class UserDto {
    private Long userId;

    @NotBlank(message = "아이디는 필수입니다.")
    @Size(min = 3, message = "아이디는 3자 이상이어야 합니다.")
    private String id;

    @NotBlank(message = "비밀번호는 필수입니다.")
    @Pattern(regexp = "^(?=.*[!@#$%^&*(),.?\":{}|<>]).{8,}$",
            message = "비밀번호는 특수문자를 포함한 8자 이상이어야 합니다.")
    private String pw;

    @NotBlank(message = "비밀번호 확인은 필수입니다.")
    private String pwCheck;  // 비밀번호 확인용 필드, DB 미저장

    @NotBlank(message = "이름은 필수입니다.")
    @Size(min = 2, message = "이름은 2글자 이상이어야 합니다.")
    private String userName;

    @NotBlank(message = "닉네임은 필수입니다.")
    @Size(min = 3, message = "닉네임은 3글자 이상이어야 합니다.")
    private String nickname;

    @NotNull(message = "생년월일은 필수입니다.")
    @Past(message = "생년월일은 과거 날짜여야 합니다.")
    private java.sql.Date birth;

    @NotBlank(message = "전화번호는 필수입니다.")
    @Pattern(regexp = "^\\d{2,3}-\\d{3,4}-\\d{4}$", message = "전화번호 형식에 맞게 입력해주세요.")
    private String phone;

    @Email(message = "유효한 이메일을 입력해주세요.")
    @NotBlank(message = "이메일은 필수입니다.")
    private String email;

    @NotBlank(message = "성별은 필수입니다.")
    private String gender;

    // 가입일시 (TIMESTAMP)
    private Timestamp joinDate;

    // 회원 상태
    private String userStatus;

    // 회원 역할
    private String userRole;

    // 온도 정보
    private Integer temperature;

    // 주소 정보 (1:1 관계)
    @Valid
    private UserAddressDto address;
}

