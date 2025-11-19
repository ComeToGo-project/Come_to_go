package com.cometogo.ctg.group.controller;


import com.cometogo.ctg.group.dto.GroupDetailDto;
import com.cometogo.ctg.group.dto.GroupDto;
import com.cometogo.ctg.group.dto.GroupRegisterDto;
import com.cometogo.ctg.group.service.GroupRegisterService;
import com.cometogo.ctg.group.service.GroupService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/groups")
public class GroupRegisterController {

    private final GroupRegisterService groupRegisterService;
    private final GroupService groupService;

    public GroupRegisterController(GroupRegisterService groupRegisterService, GroupService groupService) {
        this.groupRegisterService = groupRegisterService;
        this.groupService = groupService;
    }



    // 가입 신청 처리
    @PostMapping("/{groupId}/register")
    public String registerGroup(@PathVariable Long groupId,
                                @RequestParam(required = false) String message,
                                HttpSession session,
                                RedirectAttributes redirectAttributes) {

        Long userId = (Long) session.getAttribute("user_id");
        if (userId == null) {
            return "redirect:/user/login";
        }

        try {
            groupRegisterService.requestRegister(userId, groupId, message);
            redirectAttributes.addFlashAttribute("successMessage", "가입 신청이 완료되었습니다!");
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }

        return "redirect:/groups/" + groupId + "/register-detail";
    }
    @GetMapping("/{groupId}/register-detail")
    public String showGroupDetailForRegistration(@PathVariable Long groupId,
                                                 HttpSession session,
                                                 Model model,
                                                 @ModelAttribute("successMessage") String successMessage,
                                                 @ModelAttribute("errorMessage") String errorMessage) {


        Long loginUserId = (Long) session.getAttribute("user_id");
        GroupDetailDto groupDetail = groupService.getGroupDetailById(groupId, loginUserId);
        model.addAttribute("groupDetail", groupDetail);
        model.addAttribute("isOwner", groupDetail.isOwner());
        model.addAttribute("isMember", groupDetail.isMember());

        if (!successMessage.isEmpty()) model.addAttribute("successMessage", successMessage);
        if (!errorMessage.isEmpty()) model.addAttribute("errorMessage", errorMessage);

        return "groups/detail";
    }

    // 가입 신청 페이지 (비회원만 접근 가능)
    @GetMapping("/{groupId}/register")
    public String showRegisterPage(@PathVariable Long groupId,
                                   HttpSession session,
                                   RedirectAttributes redirectAttributes,
                                   Model model) {

        Long userId = (Long) session.getAttribute("user_id");
        if (userId == null) {
            return "redirect:/user/login";
        }

        GroupDto group = groupService.getGroupById(groupId);

        // 🔥 회장은 신청 불가
        if (group.getOwnerUserId().equals(userId)) {
            redirectAttributes.addFlashAttribute("errorMessage", "회장은 가입 신청을 할 수 없습니다.");
            return "redirect:/groups/" + groupId + "/register-detail";
        }

        // 🔥 이미 멤버인 사람도 신청 불가
        boolean isMember = groupService.isMember(userId, groupId);
        if (isMember) {
            redirectAttributes.addFlashAttribute("errorMessage", "이미 멤버입니다.");
            return "redirect:/groups/" + groupId + "/register-detail";
        }

        model.addAttribute("groupId", groupId);
        return "groups/register";
    }




    /** 🔹 회장이 가입 신청 목록 확인 */
    @GetMapping("/{groupId}/register-requests")
    public String viewRegisterRequests(@PathVariable Long groupId,
                                       HttpSession session,
                                       Model model,
                                       RedirectAttributes redirectAttributes) {

        Long userId = (Long) session.getAttribute("user_id");
        if (userId == null) return "redirect:/user/login";

        GroupDto group = groupService.getGroupById(groupId);
        if (!group.getOwnerUserId().equals(userId)) {
            redirectAttributes.addFlashAttribute("errorMessage", "권한이 없습니다.");
            return "redirect:/groups/" + groupId + "/register-detail";
        }

        List<GroupRegisterDto> requests = groupRegisterService.getRegistersByGroup(groupId);
        model.addAttribute("requests", requests);
        model.addAttribute("groupId", groupId);

        return "groups/register-requests";
    }

    // 가입 승인
    @PostMapping("/{groupId}/registers/{registerId}/approve")
    public String approveRegister(@PathVariable Long groupId,
                                  @PathVariable Long registerId,
                                  HttpSession session,
                                  RedirectAttributes redirectAttributes) {

        Long userId = (Long) session.getAttribute("user_id");
        if (userId == null) return "redirect:/user/login";

        GroupDto group = groupService.getGroupById(groupId);
        if (!group.getOwnerUserId().equals(userId)) {
            redirectAttributes.addFlashAttribute("errorMessage", "권한이 없습니다.");
            return "redirect:/groups/" + groupId + "/register-requests";
        }

        GroupRegisterDto register = groupRegisterService.getRegisterById(registerId);
        if (group.getOwnerUserId().equals(register.getUserId())) {
            redirectAttributes.addFlashAttribute("errorMessage", "회장은 이미 멤버입니다.");
            return "redirect:/groups/" + groupId + "/register-requests";
        }

        groupRegisterService.updateRegisterStatus(registerId, "APPROVED", userId);
        redirectAttributes.addFlashAttribute("successMessage", "가입이 승인되었습니다.");
        return "redirect:/groups/" + groupId + "/register-requests";
    }

    // 가입 거절
    @PostMapping("/{groupId}/registers/{registerId}/reject")
    public String rejectRegister(@PathVariable Long groupId,
                                 @PathVariable Long registerId,
                                 HttpSession session,
                                 RedirectAttributes redirectAttributes) {

        Long userId = (Long) session.getAttribute("user_id");
        if (userId == null) return "redirect:/user/login";

        GroupDto group = groupService.getGroupById(groupId);
        if (!group.getOwnerUserId().equals(userId)) {
            redirectAttributes.addFlashAttribute("errorMessage", "권한이 없습니다.");
            return "redirect:/groups/" + groupId + "/register-requests";
        }

        groupRegisterService.updateRegisterStatus(registerId, "REJECTED", userId);
        redirectAttributes.addFlashAttribute("successMessage", "가입이 거절되었습니다.");
        return "redirect:/groups/" + groupId + "/register-requests";
    }
}



