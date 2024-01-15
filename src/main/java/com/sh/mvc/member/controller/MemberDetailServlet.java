package com.sh.mvc.member.controller;

import com.sh.mvc.member.model.entity.Member;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/member/memberDetail")
public class MemberDetailServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        // 인증여부 검사
//        HttpSession session = req.getSession();
//        Member loginMember = (Member) req.getSession().getAttribute("loginMember");
//        if (loginMember == null) {
//            session.setAttribute("msg", "로그인 후 사용가능👻");
//            resp.sendRedirect(req.getContextPath() + "/");
//            return; // redirect/forward 이후 실행코드는 없어야 한다.
//        }

        req.getRequestDispatcher("/WEB-INF/views/member/memberDetail.jsp")
                .forward(req, resp);

    }
}