package com.cometogo.ctg.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AdminInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        String userRole = (session != null) ? (String) session.getAttribute("userRole") : null;

        //관리자 페이지 권한 체크
        if (!"ADMIN".equals(userRole)) {
            response.sendRedirect("/");
            return false;
        }
        return true;
    }
}
