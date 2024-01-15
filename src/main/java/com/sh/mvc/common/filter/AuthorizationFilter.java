package com.sh.mvc.common.filter;

import com.sh.mvc.member.model.entity.Member;
import com.sh.mvc.member.model.entity.Role;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 관리자 권한 검사 필터
 * - authorization 권한 : 인증받은 사용자가 이 서비스를 이용할 수 있는지 체크
 * - 검사할 url : /admin/*
 * - 인증확인 및 로그인 된 사용자의 권한 검사 Role.A인지 체크
 */

@WebFilter(urlPatterns = {"/admin/*"})

public class AuthorizationFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain) throws IOException, ServletException {
        // 인증여부 검사
        HttpSession session = req.getSession();
        Member loginMember = (Member) session.getAttribute("loginMember");
        if (loginMember == null || loginMember.getRole() != Role.A) {
            session.setAttribute("msg", "관리자만 이용가능"); /* 안전장치 잘 만들기 */
            resp.sendRedirect(req.getContextPath() + "/");
            return;
        }
        super.doFilter(req, resp, chain);
    }
}
