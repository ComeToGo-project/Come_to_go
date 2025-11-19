package com.cometogo.ctg.support.controller;

import com.cometogo.ctg.admin.dao.UserAdminDao;
import com.cometogo.ctg.support.dto.ReportDto;
import com.cometogo.ctg.support.service.ReportService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/user/support")
@RequiredArgsConstructor
public class SupportController {
    private final ReportService reportService;
    private final UserAdminDao userAdminDao;

    @GetMapping("/report")
    public String reportPage() {
        return "support/report";
    }

    @PostMapping("/report")
    public String reportSubmit(
            @ModelAttribute ReportDto form,
            @RequestParam(required = false) MultipartFile image,
            @RequestParam(required = false) String targetNickname,
            @RequestParam String reportType,
            HttpSession session,
            Model model
    ) throws IOException {
        Long reporterId = (Long) session.getAttribute("user_id");
        form.setReporterId(reporterId);
        form.setCreatedAt(LocalDateTime.now());
        form.setReportStatus("PENDING");

        if (List.of("USER", "GROUP", "MARKET", "CHAT", "BOARD", "COMMENT").contains(reportType)) {
            if (targetNickname == null || !targetNickname.isBlank()) {
                model.addAttribute("error", "대상 닉네임을 입력하세요.");
                return "support/report";
            }

            Long targetUserId = userAdminDao.findUserIdByNickname(targetNickname);
            if (targetUserId == null) {
                model.addAttribute("error", "존재하지 않는 유저입니다.");
                return "support/report";
            }
            form.setTargetId(targetUserId);
        } else {
            form.setTargetId(0L);
        }
        reportService.submitReport(form, image);
        return "redirect:/";
    }

    @GetMapping("/exist")
    @ResponseBody
    public boolean nicknameExist(@RequestParam String nickname) {
        return userAdminDao.findUserIdByNickname(nickname) != null;
    }
}
