package com.cometogo.ctg.admin.dao;

import com.cometogo.ctg.admin.dto.ReportAdminDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReportAdminDao {
    List<ReportAdminDto> findAllReports();

    int updateReportStatus(@Param("reportId") Long reportId, @Param("status") String status);
}
