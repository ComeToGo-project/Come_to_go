package com.cometogo.ctg.admin.controller;

import com.cometogo.ctg.admin.dao.AdminDashboardDao;
import com.cometogo.ctg.admin.dao.UserAdminDao;
import com.cometogo.ctg.admin.dto.AdminDashboardStatsDto;
import com.cometogo.ctg.admin.dto.UserAdminDto;
import com.cometogo.ctg.admin.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
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
        List<UserAdminDto> userList = userAdminDao.searchUsers(filterType, keyword, userStatus);
        model.addAttribute("userList", userList);
        return "admin/user_management";
    }

    @PostMapping("/user/{userId}/suspend")
    public String suspendUser(@PathVariable("userId") Long userId) {
        userAdminService.suspendUser(userId);
        return "redirect:/admin/management/user";
    }

    @PostMapping("/user/{userId}/unsuspend")
    public String unsuspendUser(@PathVariable("userId") Long userId) {
        userAdminService.unsuspendUser(userId);
        return "redirect:/admin/management/user";
    }

    //동호회 관리
    @GetMapping("/group")
    public String groupManagementPage(Model model) {
        model.addAttribute("groupList", groupAdminService.getAllGroups());
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
    public String marketManagementPage(Model model) {
        model.addAttribute("marketList", marketAdminService.getAllMarketItems());
        return "admin/market_management";
    }

    @PostMapping("/market/{itemId}/delete")
    public String deleteItem(@PathVariable Long itemId) {
        marketAdminService.deleteMarketItem(itemId);
        return "redirect:/admin/management/market";
    }

    //신고 관리
    @GetMapping("/report")
    public String reportManagementPage(Model model) {
        model.addAttribute("reportList", reportAdminService.getAllReports());
        return "admin/report_management";
    }

//    @PostMapping("/report/{reportId}/suspend")
//    public String processReport(@PathVariable Long reportid) {
//        reportAdminService.updateReportStatus(reportId, "완료");
//        return
//    }
//
//    @PostMapping("/report/{reportId}/unsuspend")
//    public String processReport(@PathVariable Long reportid) {
//
//    }
//
//    @PostMapping("/report/{reportId}/warn")
//    public String processReport(@PathVariable Long reportid) {
//
//    }

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
