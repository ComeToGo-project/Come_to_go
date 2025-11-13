package com.cometogo.ctg.group.controller;

import com.cometogo.ctg.group.service.GroupRegisterService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/groups")
public class GroupRegisterController {

    private final GroupRegisterService groupRegisterService;

    public GroupRegisterController(GroupRegisterService groupRegisterService) {
        this.groupRegisterService = groupRegisterService;
    }


    @GetMapping("/{groupId}/register")
    public String showRegisterPage(@PathVariable Long groupId, Model model) {
        model.addAttribute("groupId", groupId);
        return "groups/register";
    }

    /** 🔹 가입 신청 처리 */
    @PostMapping("/{groupId}/register")
    public String registerGroup(@PathVariable Long groupId,
                                @RequestParam(required = false) String message,
                                HttpSession session,
                                RedirectAttributes redirectAttributes) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/user/login";
        }

        try {
            groupRegisterService.requestRegister(userId, groupId, message);
            redirectAttributes.addFlashAttribute("successMessage", "가입 신청이 완료되었습니다!");
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }

        return "redirect:/groups/" + groupId + "/detail";
    }


}

