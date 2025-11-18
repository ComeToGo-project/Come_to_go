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
        return "user/index";  // 메인 페이지 뷰
    }
}
