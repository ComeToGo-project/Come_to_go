package com.cometogo.ctg.admin.service;

import com.cometogo.ctg.admin.dao.ReportAdminDao;
import com.cometogo.ctg.admin.dao.UserAdminDao;
import com.cometogo.ctg.admin.dto.ReportAdminDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportAdminService {
    private final ReportAdminDao reportAdminDao;
    private final UserAdminDao userAdminDao;

    public List<ReportAdminDto> getAllReports() {
        return reportAdminDao.findAllReports();
    }

    public void updateReportStatus(Long reportId, String status) {
        reportAdminDao.updateReportStatus(reportId, status);
    }
}
