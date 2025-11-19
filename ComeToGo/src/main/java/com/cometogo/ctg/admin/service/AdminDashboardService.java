package com.cometogo.ctg.admin.service;

import com.cometogo.ctg.admin.dao.AdminDashboardDao;
import com.cometogo.ctg.admin.dto.AdminDashboardStatsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class AdminDashboardService {

    private final AdminDashboardDao adminDashboardDao;

    public AdminDashboardStatsDto dashBoard() {
        return adminDashboardDao.getDashboardStats(LocalDate.now().toString());
    }
}
