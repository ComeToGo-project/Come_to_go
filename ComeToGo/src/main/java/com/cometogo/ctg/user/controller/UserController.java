package com.cometogo.ctg.user.controller;


import com.cometogo.ctg.user.dto.UserAddressDto;
import com.cometogo.ctg.user.dto.UserDto;
import com.cometogo.ctg.user.service.UserService;
import com.cometogo.ctg.user.service.UserVerifyService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.beans.PropertyEditorSupport;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    public UserService userService;


    public UserVerifyService userVerifyService;


    public UserController(UserService userService , UserVerifyService userVerifyService) {
        this.userService = userService;
        this.userVerifyService = userVerifyService;
    }

    @GetMapping("/login")
    public String loginForm() {
        return "user/login";
    }

    @GetMapping("/join")
    public String joinForm(Model model,HttpSession session) {
        model.addAttribute("userDto", new UserDto());

        // 세션에 인증된 이메일이 있으면 모델에 추가
        String verifiedEmail = (String) session.getAttribute("verifiedEmail");
        if (verifiedEmail != null) {
            model.addAttribute("verifiedEmail", verifiedEmail);
        }

        return "user/join";
    }

    @PostMapping("/join")
    public String joinSubmit(@Valid @ModelAttribute UserDto userDto,
                             BindingResult bindingResult, Model model, RedirectAttributes redirectAttributes, HttpSession session) {

        // 세션에서 인증된 이메일 확인
        String verifiedEmail = (String) session.getAttribute("verifiedEmail");

        // 이메일 인증 확인
        if (verifiedEmail == null || !verifiedEmail.equals(userDto.getEmail())) {
            model.addAttribute("error", "이메일 인증이 필요합니다.");
            model.addAttribute("verifiedEmail", verifiedEmail);
            return "user/join";
        }

        // 기본 유효성 검사 실패 처리
        if (bindingResult.hasErrors()) {
            model.addAttribute("verifiedEmail", verifiedEmail);
            return "user/join";
        }

        // 아이디 중복검사
        if (userService.isIdDuplicate(userDto.getId())) {
            model.addAttribute("idDuplicateError", "이미 존재하는 아이디입니다.");
            model.addAttribute("verifiedEmail", verifiedEmail);
            return "user/join";
        }

        // 이메일 중복검사
        if (userService.isEmailDuplicate(userDto.getEmail())) {
            model.addAttribute("emailDuplicateError", "이미 사용 중인 이메일입니다.");
            model.addAttribute("verifiedEmail", verifiedEmail);
            return "user/join";
        }

        // 닉네임 중복검사
        if (userService.isNicknameDuplicate(userDto.getNickname())) {
            model.addAttribute("nicknameDuplicateError", "이미 사용 중인 닉네임입니다.");
            model.addAttribute("verifiedEmail", verifiedEmail);
            return "user/join";
        }

        // 비밀번호와 비밀번호 확인 값 일치 여부 체크 추가
        if (!userDto.getPw().equals(userDto.getPwCheck())) {
            model.addAttribute("pwError", "비밀번호와 비밀번호 확인이 다릅니다.");
            model.addAttribute("verifiedEmail", verifiedEmail);
            return "user/join";
        }
        // 회원가입 서비스 호출
        if (!userService.joinUser(userDto)) {
            model.addAttribute("verifiedEmail", verifiedEmail);
            return "user/join";
        }

        // 회원가입 성공 후 세션에서 인증 정보 제거
        session.removeAttribute("verifiedEmail");
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
            session.setAttribute("user_role", user.getUserRole());
            return "redirect:/"; // 로그인 성공 후 이동 페이지
        }

        model.addAttribute("loginError", "아이디 또는 비밀번호가 올바르지 않습니다.");
        return "user/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();  // 세션 무효화하여 모든 세션 데이터 삭제
        return "redirect:/";  // 로그아웃 후 메인 페이지로 리다이렉트
    }

    @GetMapping("/login/find-id")
    public String findIdForm(HttpSession session) {
        return "user/findid";
    }
    @GetMapping("/login/find-password")
    public String findPwForm(HttpSession session,Model model) {
        return "user/findpw";
    }

    // 아이디 찾기 처리
    @PostMapping("/find-id")
    @ResponseBody
    public Map<String, Object> findId(@RequestParam String userName, @RequestParam String email) {
        String userId = userService.findIdByNameAndEmail(userName, email);

        if (userId != null) {
            return Map.of("success", true, "userId", userId);
        } else {
            return Map.of("success", false, "message", "일치하는 회원 정보를 찾을 수 없습니다.");
        }
    }

    // 비밀번호 재설정 인증 코드 발송
    @PostMapping("/send-password-reset-code")
    @ResponseBody
    public Map<String, Object> sendPasswordResetCode(@RequestParam String userId, @RequestParam String email) {
        boolean exists = userService.checkUserExists(userId, email);

        if (!exists) {
            return Map.of("success", false, "message", "일치하는 회원 정보를 찾을 수 없습니다.");
        }

        try {
            userVerifyService.sendVerificationEmail(email);
            return Map.of("success", true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return Map.of("success", false, "message", "인증 코드 발송에 실패했습니다.");
        }
    }

    // 비밀번호 재설정 인증 코드 확인
    @PostMapping("/verify-password-reset-code")
    @ResponseBody
    public Map<String, Object> verifyPasswordResetCode(@RequestParam String userId,
                                                       @RequestParam String email,
                                                       @RequestParam String code) {
        String result = userVerifyService.verifyCode(email, code);

        if ("success".equals(result) || "already".equals(result)) {
            return Map.of("success", true);
        } else {
            return Map.of("success", false, "message", "인증 코드가 일치하지 않거나 만료되었습니다.");
        }
    }

    //비밀번호 재설정
    @PostMapping("/reset-password")
    @ResponseBody
    public Map<String, Object> resetPassword(@RequestParam String userId,
                                             @RequestParam String newPassword,
                                             @RequestParam String confirmPassword) {

        Map<String, Object> result = new HashMap<>();

        // 비밀번호 검증 정규식
        String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>]).{8,}$";

        // 1) 형식 검사
        if (!newPassword.matches(passwordPattern)) {
            result.put("success", false);
            result.put("message", "비밀번호는 최소 8자 이상이며, 영문 대/소문자, 숫자, 특수문자를 모두 포함해야 합니다.");
            return result;
        }

        // 2) 일치 검사
        if (!newPassword.equals(confirmPassword)) {
            result.put("success", false);
            result.put("message", "새 비밀번호가 일치하지 않습니다.");
            return result;
        }

        // 3) DB 업데이트
        boolean success = userService.updatePassword(userId, newPassword);

        if (!success) {
            result.put("success", false);
            result.put("message", "비밀번호 변경에 실패했습니다. 다시 시도해주세요.");
            return result;
        }

        // 4) 성공
        result.put("success", true);
        return result;
    }

    @GetMapping("/mypage")
    public String mypage(Model model ,HttpSession session) {
        Long userId = (Long) session.getAttribute("user_id"); // 세션 키 이름은 프로젝트마다 다름
        if (userId == null) {
            return "redirect:/user/login";
        }

        model.addAttribute("userName",userService.findByName(userId));
        model.addAttribute("nickname",userService.findByNickName(userId));
        model.addAttribute("id", userService.findById(userId));
        return "user/mypage";
    }

    @GetMapping("/mypage/change-password")
    public String mypageChangePassword(Model model ,HttpSession session) {
        return "user/changepw";
    }

    @Transactional
    @PostMapping("/mypage/change-password")
    public String changePassword(@RequestParam String currentPw,
                                 @RequestParam String newPw,
                                 @RequestParam String newPwConfirm,
                                 HttpSession session,
                                 Model model,
                                 RedirectAttributes redirectAttrs) {
        Long userId = (Long) session.getAttribute("user_id");
        if (userId == null) {
            return "redirect:/user/login";
        }

        String passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*(),.?\":{}|<>]).{8,}$";

        if (!newPw.matches(passwordPattern)) {
            model.addAttribute("error", "비밀번호는 최소 8자 이상이며, 영문 대/소문자, 숫자, 특수문자를 모두 포함해야 합니다.");
            return "user/changepw";
        }

        if (!newPw.equals(newPwConfirm)) {
            model.addAttribute("error", "새 비밀번호가 일치하지 않습니다.");
            return "user/changepw";  // 다시 변경 폼으로 돌아감
        }

        // 실제 비밀번호 변경 로직 (예: DB에서 현재 비밀번호 확인, 변경 처리)
        boolean success = userService.changePassword(userId, currentPw,newPw);

        if (!success) {
            model.addAttribute("error", "현재 비밀번호가 올바르지 않습니다.");
            return "user/changepw";
        }

        redirectAttrs.addFlashAttribute("success", "비밀번호가 변경되었습니다.");
        return "redirect:/user/mypage";
    }

@GetMapping("/mypage/change-nickname")
public String  changeNickname() {
        return "user/changenickname";
}

    @PostMapping("/mypage/change-nickname")
    public String changeNickname(@RequestParam String nickname, HttpSession session, Model model,RedirectAttributes redirectAttrs) {
        Long userId = (Long) session.getAttribute("user_id");
        if (userId == null) {
            return "redirect:/user/login";
        }

        // 닉네임 유효성 검사
        if (nickname == null || nickname.trim().isEmpty()) {
            model.addAttribute("error", "닉네임을 입력해주세요.");
            return "user/changenickname";
        }

        // 3글자 이상, 한글/영문/숫자만 허용 정규식
        String nicknamePattern = "^[가-힣a-zA-Z0-9]{3,}$";

        if (!nickname.matches(nicknamePattern)) {
            model.addAttribute("error", "닉네임은 3글자 이상이며 한글, 영문, 숫자만 사용할 수 있습니다.");
            return "user/changenickname";
        }

        // 중복 검사 (예: userService.checkNicknameExists)
        if (userService.isNicknameDuplicate(nickname)) {
            model.addAttribute("error", "이미 사용 중인 닉네임입니다.");
            return "user/changenickname";
        }

        boolean updated = userService.updateNickname(userId, nickname);
        if (!updated) {
            model.addAttribute("error", "닉네임 변경에 실패했습니다. 다시 시도하세요.");
            return "user/changenickname";
        }

        redirectAttrs.addFlashAttribute("success", "닉네임이 변경되었습니다.");
        return "redirect:/user/mypage";
    }

@GetMapping("/mypage/change-address")
public String changeAddress(Model model) {
    model.addAttribute("userAddressDto", new UserAddressDto());

    return "user/changeaddress";
}

    @PostMapping("/mypage/change-address")
    public String changeAddress(@Valid @ModelAttribute UserAddressDto addressDto,
                                BindingResult bindingResult,
                                HttpSession session,
                                Model model,
                                RedirectAttributes redirectAttrs) {

        Long userId = (Long) session.getAttribute("user_id");
        if (userId == null) {
            return "redirect:/user/login";
        }

        addressDto.setUserId(userId);

        if (bindingResult.hasErrors()) {
            model.addAttribute("error", "모든 주소를 올바르게 입력해주세요.");
            return "user/changeaddress"; // 주소 변경 폼 뷰 이름
        }

        boolean success = userService.updateAddress(addressDto);
        if (!success) {
            model.addAttribute("error", "주소 변경에 실패했습니다. 다시 시도해주세요.");
            return "user/changeaddress";
        }

        redirectAttrs.addFlashAttribute("success", "주소가 변경되었습니다.");
        return "redirect:/user/mypage";
    }




    // 날짜 유효성 검사
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(java.sql.Date.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                if (text == null || text.trim().isEmpty()) {
                    setValue(null);
                } else {
                    setValue(java.sql.Date.valueOf(text));
                }
            }
        });
    }
}

