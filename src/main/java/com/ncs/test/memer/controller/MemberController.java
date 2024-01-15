package com.ncs.test.memer.controller;

import com.ncs.test.memer.model.service.MemberService;
import com.ncs.test.memer.model.vo.Member;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;

public class MemberController extends HttpServlet {
    private MemberService memberService = new MemberService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");

        String id = req.getParameter("memberId");
        String pwd = req.getParameter("memberPwd");
        String name = req.getParameter("memberName");
        Date date = Date.valueOf(req.getParameter("memberEnrollDate"));

        Member member = new Member(id, pwd, name, date);

        int result = memberService.signUp(member);

        if (result > 0) {
            req.getSession().setAttribute("msg", "회원가입 성공");
        } else if (result < 0){
            req.getSession().setAttribute("msg", "회원가입 과정에서 오류 발생");
        } else {
            req.getSession().setAttribute("msg", "회원가입 실패");
        }

        resp.sendRedirect(req.getContextPath() + "/");
    }
}
