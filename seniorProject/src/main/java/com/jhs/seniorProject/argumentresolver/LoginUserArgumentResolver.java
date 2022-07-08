package com.jhs.seniorProject.argumentresolver;

import com.jhs.seniorProject.controller.SessionConst;
import com.jhs.seniorProject.domain.User;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {
    //TODO 컨트롤러에 user entity 보다는 loginUser 새로 만들어서 반환
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Login.class)
                && User.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpSession session = ((HttpServletRequest) webRequest.getNativeRequest()).getSession();
        return session == null ? null : session.getAttribute(SessionConst.LOGIN_USER);
    }
}
