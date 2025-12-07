package com.cometogo.ctg.admin.service;

import com.cometogo.ctg.admin.dao.ReportAdminDao;
import com.cometogo.ctg.admin.dao.UserAdminDao;
import com.cometogo.ctg.admin.dto.ReportAdminDto;
import com.cometogo.ctg.support.dto.ReportDto;
import com.cometogo.ctg.support.dto.ReportImageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportAdminService {
    private final ReportAdminDao reportAdminDao;
    private final UserAdminDao userAdminDao;

    public List<ReportAdminDto> getAllReports(String keyword, String reportType, String reportStatus) {
        return reportAdminDao.findReports(keyword, reportType, reportStatus);
    }

    public void updateReportStatus(Long reportId, String status) {
        reportAdminDao.updateReportStatus(reportId, status);
    }

    @Transactional(readOnly = true)
    public ReportDto getReport(Long reportId) {
        return reportAdminDao.findById(reportId);
    }

    @Transactional(readOnly = true)
    public List<ReportImageDto> getReportImages(Long reportId) {
        return reportAdminDao.findByReportId(reportId);
    }
}
