package com.jhs.seniorProject.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static com.jhs.seniorProject.controller.SessionConst.LOGIN_USER;

@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String requestURI = request.getRequestURI();
        log.info("loginInterceptor execute {}", requestURI);

        HttpSession session = request.getSession(false);
        if (cannotAccessThisURI(session)) {
            log.info("cannot access this {}", requestURI);
            response.sendRedirect("/users/login?redirectURL="+requestURI);
            return false;
        }
        return true;
    }

    private boolean cannotAccessThisURI(HttpSession session) {
        return session == null || session.getAttribute(LOGIN_USER) == null;
    }
}
