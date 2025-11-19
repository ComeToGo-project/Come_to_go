package com.cometogo.ctg.support.service;

import com.cometogo.ctg.support.dao.ReportDao;
import com.cometogo.ctg.support.dto.ReportDto;
import com.cometogo.ctg.support.dto.ReportImageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReportService {
    private final ReportDao reportDao;

    @Transactional
    public void submitReport(ReportDto reportDto, MultipartFile file) throws IOException {
        reportDao.insertReport(reportDto);
        Long reportId = reportDto.getReportId();

        if (file != null && !file.isEmpty()) {
            String savePath = saveImageFile(file);
            ReportImageDto imgDto = new ReportImageDto();
            imgDto.setReportId(reportId);
            imgDto.setFilePath(savePath);
            imgDto.setOriginalName(file.getOriginalFilename());
            imgDto.setUploadedAt(LocalDateTime.now());
            reportDao.insertReportImage(imgDto);
        }
    }

    private String saveImageFile(MultipartFile file) throws IOException {
        String uploadDir = new File("src/main/resources/static/report-images/").getAbsolutePath() + "/";
        String newFileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        file.transferTo(new File(uploadDir + newFileName));
        return "/report-images/" + newFileName;
    }
}
