package com.cometogo.ctg.support.dao;

import com.cometogo.ctg.support.dto.ReportDto;
import com.cometogo.ctg.support.dto.ReportImageDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReportDao {
    int insertReport(ReportDto report);
    int insertReportImage(ReportImageDto reportImage);
}
