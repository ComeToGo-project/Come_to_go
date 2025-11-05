package com.cometogo.ctg.admin.dao;

import com.cometogo.ctg.admin.dto.AdminDashboardStatsDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AdminDashboardDao {
    AdminDashboardStatsDto getDashboardStats(@Param("startDate") String startDate);
}
