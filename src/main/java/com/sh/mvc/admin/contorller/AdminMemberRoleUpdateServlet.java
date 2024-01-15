package com.sh.mvc.admin.contorller;

import com.sh.mvc.member.model.entity.Member;
import com.sh.mvc.member.model.entity.Role;
import com.sh.mvc.member.model.service.MemberService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/admin/updateMemberRole")
public class AdminMemberRoleUpdateServlet extends HttpServlet {
    private MemberService memberService = new MemberService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. 사용자 입력값 처리
        String id = req.getParameter("id");
        String _role = req.getParameter("role");
        Role role = Role.valueOf(_role);

        Member member = new Member();
        member.setId(id);
        member.setRole(role);
        System.out.println(member);
        // 2. 업무로직
        int result = memberService.updateMemberRole(member);
        // 리다이렉트후에 사용자피드백
        req.getSession().setAttribute("msg", "회원권한을 성공적으로 업데이트😜");


        // 3. redirect
        resp.sendRedirect(req.getContextPath() + "/admin/memberList");
    }
}