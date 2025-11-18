package com.cometogo.ctg.admin.controller;

import com.cometogo.ctg.admin.dao.*;
import com.cometogo.ctg.admin.dto.*;
import com.cometogo.ctg.admin.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/management")
public class AdminController {

    private final UserAdminService userAdminService;
    private final AdminDashboardDao adminDashboardDao;
    private final UserAdminDao userAdminDao;
    private final GroupAdminService groupAdminService;
    private final SystemAdminService systemAdminService;
    private final MarketAdminService marketAdminService;
    private final ReportAdminService reportAdminService;
    private final GroupAdminDao groupAdminDao;
    private final MarketAdminDao marketAdminDao;
    private final ReportAdminDao reportAdminDao;

    @GetMapping
    public String adminPage(Model model) {
        dashBoard(model);
        return "admin/manager";
    }

    //사용자 관리
    @GetMapping("/user")
    public String userManagementPage(
            @RequestParam(required = false) String filterType,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String userStatus,
            Model model
    ) {
        List<UserAdminDto> userList = userAdminService.getUsers(filterType, keyword, userStatus);
        model.addAttribute("userList", userList);
        return "admin/user_management";
    }

    @PostMapping("/user/{userId}/suspend")
    public String suspendUser(@PathVariable("userId") Long userId,
                              @RequestParam("banDays") int banDays) {
        userAdminService.banUser(userId, banDays);
        return "redirect:/admin/management/user";
    }

    @PostMapping("/user/{userId}/unsuspend")
    public String unsuspendUser(@PathVariable("userId") Long userId) {
        userAdminService.unbanUser(userId);
        return "redirect:/admin/management/user";
    }

    //동호회 관리
    @GetMapping("/group")
    public String groupManagementPage(
            @RequestParam(required = false) String filterType,
            @RequestParam(required = false) String keyword,
            Model model) {
        List<GroupAdminDto> groupList = groupAdminService.getGroups(filterType, keyword);
        model.addAttribute("groupList", groupList);
        return "admin/group_management";
    }

    @PostMapping("/group/{groupId}/delete")
    public String deleteGroup(@PathVariable Long groupId) {
        groupAdminService.deleteGroup(groupId);
        return "redirect:/admin/management/group";
    }

    @PostMapping("/group/{groupId}/warn")
    public String warnGroup(@PathVariable Long groupId) {
        groupAdminService.addWarning(groupId);
        return "redirect:/admin/management/group";
    }

    //중고거래 관리
    @GetMapping("/market")
    public String marketManagementPage(
            @RequestParam(required = false) String filterType,
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String status,
            Model model) {
        List<MarketAdminDto> marketList = marketAdminService.getMarketItems(filterType, keyword, status);
        model.addAttribute("marketList", marketList);
        return "admin/market_management";
    }

    @PostMapping("/market/{itemId}/delete")
    public String deleteItem(@PathVariable Long itemId) {
        marketAdminService.deleteMarketItem(itemId);
        return "redirect:/admin/management/market";
    }

    //신고 관리
    @GetMapping("/report")
    public String reportManagementPage(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String reportType,
            @RequestParam(required = false) String reportStatus,
            Model model) {
        List<ReportAdminDto> reportList = reportAdminService.getReports(keyword, reportType, reportStatus);
        model.addAttribute("reportList", reportList);
        return "admin/report_management";
    }

    @PostMapping("/report/{reportId}/status")
    public String updateReportStatus(@PathVariable Long reportId,
                                     @RequestParam("reportStatus") String reportStatus) {
        reportAdminService.updateReportStatus(reportId, reportStatus);
        return "redirect:/admin/report_management";
    }

    //시스템 관리
    @GetMapping("/system")
    public String systemManagementPage(Model model) {
        model.addAttribute("categories", systemAdminService.getAllCategories());
        return "admin/system_management";
    }

    @PostMapping("/system/group-category/add")
    public String addCategory(@RequestParam String categoryName) {
        systemAdminService.addCategory(categoryName);
        return "redirect:/admin/management/system";
    }

    @PostMapping("/system/group-category/delete")
    public String deleteCategory(@RequestParam Long categoryId) {
        systemAdminService.deleteCategory(categoryId);
        return "redirect:/admin/management/system";
    }

    private void dashBoard(Model model) {
        String today = LocalDate.now().toString();
        AdminDashboardStatsDto stats = adminDashboardDao.getDashboardStats(today);
        model.addAttribute("stats", stats);
    }
}
