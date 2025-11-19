package com.cometogo.ctg.admin.controller;

import com.cometogo.ctg.admin.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/management")
public class AdminController {

    private final UserAdminService userAdminService;
    private final GroupAdminService groupAdminService;
    private final SystemAdminService systemAdminService;
    private final MarketAdminService marketAdminService;
    private final ReportAdminService reportAdminService;
    private final AdminDashboardService adminDashboardService;

    @GetMapping
    public String adminPage(Model model) {
        model.addAttribute("stats", adminDashboardService.dashBoard());
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
        model.addAttribute("userList", userAdminService.getUsers(filterType, keyword, userStatus));
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
        model.addAttribute("groupList", groupAdminService.getGroups(filterType, keyword));
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
        model.addAttribute("marketList", marketAdminService.getMarketItems(filterType, keyword, status));
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
        model.addAttribute("reportList", reportAdminService.getReports(keyword, reportType, reportStatus));
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

}
