package com.cometogo.ctg.user.controller;


import com.cometogo.ctg.user.dto.UserDto;
import com.cometogo.ctg.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/user")
public class UserController {

    public UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @GetMapping("/join")
    public String joinForm(Model model) {
        model.addAttribute("userDto", new UserDto());
        return "join";
    }

    @PostMapping("/join")
    public String joinSubmit(@Valid @ModelAttribute UserDto userDto,
                             BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes) {
        // 기본 유효성 검사 실패 처리
        if (bindingResult.hasErrors()) {
            return "join";
        }

        // 아이디 중복검사
        if (userService.isIdDuplicate(userDto.getId())) {
            model.addAttribute("idDuplicateError", "이미 존재하는 아이디입니다.");
            return "join";
        }

        // 이메일 중복검사
        if (userService.isEmailDuplicate(userDto.getEmail())) {
            model.addAttribute("emailDuplicateError", "이미 사용 중인 이메일입니다.");
            return "join";
        }

        // 닉네임 중복검사
        if (userService.isNicknameDuplicate(userDto.getNickname())) {
            model.addAttribute("nicknameDuplicateError", "이미 사용 중인 닉네임입니다.");
            return "join";
        }

        // 비밀번호와 비밀번호 확인 값 일치 여부 체크 추가
        if (!userDto.getPw().equals(userDto.getPwCheck())) {
            model.addAttribute("pwError", "비밀번호와 비밀번호 확인이 다릅니다.");
            return "join";
        }
        // 회원가입 서비스 호출
        if (!userService.joinUser(userDto)) {
            return "join";
        }

        // 회원가입 성공 메시지 저장
        redirectAttributes.addFlashAttribute("joinSuccessMsg", "회원가입에 성공했습니다. \n로그인 페이지로 이동합니다.");

        // 로그인 페이지로 리다이렉트
        return "redirect:/user/login";
    }

    @PostMapping("/login")
    public String loginSubmit(@RequestParam(name="id") String id,
                              @RequestParam(name="pw") String pw,
                              HttpSession session,
                              Model model) {
        UserDto user = userService.login(id, pw);
        if (user != null) {
            session.setAttribute("user_id", user.getUserId());
            System.out.println("로그인 성공");
            System.out.println(session.getAttribute("user_id"));
            System.out.println(user.getUserId());
            return "redirect:/"; // 로그인 성공 후 이동 페이지
        }

        model.addAttribute("loginError", "아이디 또는 비밀번호가 올바르지 않습니다.");
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();  // 세션 무효화하여 모든 세션 데이터 삭제
        return "redirect:/";  // 로그아웃 후 메인 페이지로 리다이렉트
    }
}

