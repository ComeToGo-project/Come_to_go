package com.cometogo.ctg.admin.dao;

import com.cometogo.ctg.admin.dto.ReportAdminDto;
import com.cometogo.ctg.support.dto.ReportDto;
import com.cometogo.ctg.support.dto.ReportImageDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReportAdminDao {
    List<ReportAdminDto> findReports(
            @Param("keyword") String keyword,
            @Param("reportType") String reportType,
            @Param("reportStatus") String reportStatus
    );

    int updateReportStatus(
            @Param("reportId") Long reportId,
            @Param("reportStatus") String reportStatus);

    ReportDto findById(Long reportId);

    List<ReportImageDto> findByReportId(Long reportId);
}
