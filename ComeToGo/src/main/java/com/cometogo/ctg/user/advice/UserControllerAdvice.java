package com.cometogo.ctg.user.advice;

import com.cometogo.ctg.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class UserControllerAdvice {

    private final UserService userService;

    UserControllerAdvice(UserService userService) {
        this.userService = userService;
    }
    @ModelAttribute("nickname")
    public String addNickname(HttpSession session) {
        Long userId = (Long) session.getAttribute("user_id");
        if (userId != null) {
            return userService.getNicknameByUserId(userId);  // DB에서 조회
        }
        return null;
    }
}
