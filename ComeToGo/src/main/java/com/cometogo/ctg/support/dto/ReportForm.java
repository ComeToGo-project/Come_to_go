package com.cometogo.ctg.support.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class ReportForm {

    @NotBlank(message = "문의/신고 유형을 선택해주세요.")
    private String reportType;

    @NotBlank(message = "문의/신고 사유를 입력해주세요.")
    private String reason;

    @NotBlank(message = "문의/신고 내용을 입력해주세요.")
    private String contents;

    private Long targetId;
    private String targetNickname;

    @NotNull
    @Size(min = 1, max = 5, message = "첨부파일은 1개 이상 5개 이하로 등록해주세요.")
    private List<MultipartFile> images;

    private String email;
}
