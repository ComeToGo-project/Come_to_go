package com.cometogo.ctg.support.controller;

import com.cometogo.ctg.admin.dao.GroupAdminDao;
import com.cometogo.ctg.admin.dao.UserAdminDao;
import com.cometogo.ctg.support.dto.ReportDto;
import com.cometogo.ctg.support.dto.ReportForm;
import com.cometogo.ctg.support.service.ReportService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    private final GroupAdminDao groupAdminDao;

    @GetMapping({"/report"})
    public String reportPage(Model model, HttpSession session) {
        Long userId = (Long)session.getAttribute("user_id");
        String email = userAdminDao.findUserEmailById(userId);
        ReportForm form = new ReportForm();
        form.setEmail(email);
        form.setTargetNickname("");
        model.addAttribute("form", form);
        return "support/report";
    }

    @PostMapping({"/report"})
    public String reportSubmit(
            @ModelAttribute @Valid ReportForm form,
            BindingResult bindingResult,
            HttpSession session,
            Model model) throws IOException {
        Long reporterId = (Long)session.getAttribute("user_id");
        if (reporterId == null) {
            model.addAttribute("error", "로그인이 필요합니다.");
            model.addAttribute("form", form);
            return "support/report";
        } else if (bindingResult.hasErrors()) {
            model.addAttribute("error", bindingResult.getFieldError().getDefaultMessage());
            model.addAttribute("form", form);
            return "support/report";
        } else {
            String type = form.getReportType();
            if (!List.of("USER", "GROUP", "MARKET", "CHAT", "BOARD", "COMMENT").contains(type) || form.getTargetNickname() != null && !form.getTargetNickname().isBlank()) {
                if (List.of("USER", "MARKET", "CHAT", "BOARD", "COMMENT").contains(type)) {
                    Long targetUserId = userAdminDao.findUserIdByNickname(form.getTargetNickname());
                    if (targetUserId == null) {
                        model.addAttribute("error", "존재하지 않는 유저입니다.");
                        model.addAttribute("form", form);
                        return "support/report";
                    }

                    form.setTargetId(targetUserId);
                } else if ("GROUP".equals(type)) {
                    Long ownerUserId = groupAdminDao.findOwnerUserIdByNickname(form.getTargetNickname());
                    if (ownerUserId == null) {
                        model.addAttribute("error", "해당 닉네임의 동호회장이 없습니다.");
                        model.addAttribute("form", form);
                        return "support/report";
                    }

                    form.setTargetId(ownerUserId);
                } else {
                    form.setTargetId(reporterId);
                }

                if (form.getTargetId() == null) {
                    model.addAttribute("error", "신고 대상 설정 중 오류가 발생했습니다.");
                    model.addAttribute("form", form);
                    return "support/report";
                } else {
                    ReportDto reportDto = convertToReportDto(form, reporterId);
                    this.reportService.submitReport(reportDto, (MultipartFile[])form.getImages().toArray(new MultipartFile[0]));
                    return "redirect:/";
                }
            } else {
                bindingResult.rejectValue("targetNickname", "required", "신고 대상을 입력해주세요.");
                model.addAttribute("error", "신고 대상을 입력해주세요.");
                model.addAttribute("form", form);
                return "support/report";
            }
        }
    }

    @GetMapping({"my-reports"})
    public String myReportsList(Model model) {
        return "support/my_reports";
    }

    private ReportDto convertToReportDto(ReportForm form, Long reporterId) {
        ReportDto dto = new ReportDto();
        dto.setReporterId(reporterId);
        dto.setTargetId(form.getTargetId());
        dto.setTargetName(form.getTargetNickname());
        dto.setReportType(form.getReportType());
        dto.setReason(form.getReason());
        dto.setContents(form.getContents());
        dto.setEmail(form.getEmail());
        dto.setCreatedAt(LocalDateTime.now());
        dto.setReportStatus("PENDING");
        return dto;
    }
}
