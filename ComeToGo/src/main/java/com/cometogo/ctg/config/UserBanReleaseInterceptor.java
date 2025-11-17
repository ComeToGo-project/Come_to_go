package com.cometogo.ctg.config;

import com.cometogo.ctg.admin.service.UserAdminService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class UserBanReleaseInterceptor implements HandlerInterceptor {
    private final UserAdminService userAdminService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        Long userId = (session != null) ? (Long) session.getAttribute("user_id") : null;

        //로그인시 정지 상태 자동 해제 시도
        if (userId != null) {
            userAdminService.checkAndReleaseUserBan(userId);
        }
        return true;
    }
}
