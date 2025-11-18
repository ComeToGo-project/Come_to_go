package com.cometogo.ctg.user.controller;

import com.cometogo.ctg.user.service.UserService;
import com.cometogo.ctg.user.service.UserVerifyService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.beans.PropertyEditorSupport;
import java.util.Map;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserVerifyController {

    private final UserVerifyService verifyService;
    private final UserService userService;
    // 인증 코드 발송
    @PostMapping("/send-verify-code")
    @ResponseBody
    public Map<String, Object> sendVerifyCode(@RequestParam String email) {
        // 이메일 중복 검사
        if (userService.isEmailDuplicate(email)) {
            return Map.of("success", false, "message", "이미 사용 중인 이메일입니다.");
        }

        System.out.println("인증요청");
        try {
            verifyService.sendVerificationEmail(email);
            System.out.println("인증완료");
            return Map.of("success", true, "message", "인증 코드가 발송되었습니다.");
        } catch (Exception e) {
            System.out.println("발송 실패");
            System.out.println(e.getMessage());
            return Map.of("success", false, "message", "발송 실패");
        }
    }

    // 인증 코드 확인
    @PostMapping("/verify-code")
    @ResponseBody
    public Map<String, Object> verifyCode(@RequestParam String email,
                                          @RequestParam String code,
                                          HttpSession  session) {
        String result = verifyService.verifyCode(email, code);

        return switch (result) {
            case "success" -> {
                // 세션에 인증된 이메일 저장
                session.setAttribute("verifiedEmail", email);
                yield Map.of("success", true, "message", "인증이 완료되었습니다.");
            }
            case "already" -> {
                // 이미 인증된 경우에도 세션에 저장
                session.setAttribute("verifiedEmail", email);
                yield Map.of("success", true, "message", "이미 인증된 이메일입니다.");
            }
            case "expired" -> Map.of("success", false, "message", "인증 코드가 만료되었습니다.");
            default -> Map.of("success", false, "message", "인증 코드가 일치하지 않습니다.");
        };
    }
}
