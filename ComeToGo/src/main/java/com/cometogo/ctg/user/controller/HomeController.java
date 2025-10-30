package com.cometogo.ctg.user.controller;

import com.cometogo.ctg.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    public UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String mainPage(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("user_id");
        if (userId != null) {
            String nickname = userService.getNicknameByUserId(userId);
            if (nickname != null) {
                model.addAttribute("nickname", nickname);
            }
        }
        return "index";  // 메인 페이지 뷰
    }
}
