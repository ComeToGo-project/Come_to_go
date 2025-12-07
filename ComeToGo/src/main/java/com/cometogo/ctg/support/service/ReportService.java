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
    public void submitReport(ReportDto reportDto, MultipartFile[] files) throws IOException {
        reportDao.insertReport(reportDto);
        Long reportId = reportDto.getReportId();
        if (files != null) {
            for(MultipartFile file : files) {
                if (file != null && !file.isEmpty()) {
                    String savePath = saveImageFile(file);
                    ReportImageDto imgDto = new ReportImageDto();
                    imgDto.setReportId(reportId);
                    imgDto.setFilePath(savePath);
                    imgDto.setOriginalName(file.getOriginalFilename());
                    imgDto.setFileSize(file.getSize());
                    imgDto.setUploadedAt(LocalDateTime.now());
                    reportDao.insertReportImage(imgDto);
                }
            }

        }
    }

    private String saveImageFile(MultipartFile file) throws IOException {
        File var10000 = new File("src/main/resources/static/report-images/");
        String uploadDir = var10000.getAbsolutePath() + "/";
        UUID var5 = UUID.randomUUID();
        String newFileName = var5 + "_" + file.getOriginalFilename();
        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        file.transferTo(new File(uploadDir + newFileName));
        return "/report-images/" + newFileName;
    }
}
