package com.sh.mvc.member.controller;

import com.sh.mvc.common.HelloMvcUtils;
import com.sh.mvc.member.model.entity.Gender;
import com.sh.mvc.member.model.entity.Member;
import com.sh.mvc.member.model.entity.Role;
import com.sh.mvc.member.model.service.MemberService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

@WebServlet("/member/memberRegister")
public class MemberRegisterServlet extends HttpServlet {
    private MemberService memberService = new MemberService();
// 405오류는 Get준비하고 Post로 받을준비해서 발생 | Post준비하고 Get으로 받을준비해서 발생
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/WEB-INF/views/member/memberRegister.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. 인코딩처리
//        req.setCharacterEncoding("utf-8");

        // 2. 사용자입력값 가져오기
        // id, password, name, birthday, email, gender, hobby
        String id = req.getParameter("id");
//        String password = req.getParameter("password");
//        String password = HelloMvcUtils.getEncryptedPassword(req.getParameter("password"));
        String password = HelloMvcUtils.getEncryptedPassword(req.getParameter("password"), id); // salt추가해서 보안강화
        String name = req.getParameter("name");
        String _birthday = req.getParameter("birthday");
        String email = req.getParameter("email");
        String phone = req.getParameter("phone");
        String _gender = req.getParameter("gender");
        String[] _hobby = req.getParameterValues("hobby");
        System.out.println(id + ", " + password + ", " + name + ", " + _birthday + ", " + email + ", " + phone + ", " + _gender + ", " + _hobby);

        // String으로 받았으니까 값 바꿔주기
        LocalDate birthday = _birthday != null && !"".equals(_birthday) ?
                LocalDate.parse(_birthday, DateTimeFormatter.ISO_DATE) :
                    null;
        Gender gender = _gender != null ? Gender.valueOf(_gender) : null;
        List<String> hobby = _hobby != null ? Arrays.asList(_hobby) : null;

        Member member = new Member(id, password, name, Role.U, gender, birthday, email, phone, hobby, 0, null);
        System.out.println(member);


        // 3. 업무로직
        int result = memberService.insertMember(member);

        // 리다이렉트 후 경고창으로 성공메세지 전달
        req.getSession().setAttribute("msg", "회원가입성공😁");

        // 4. view(forward) | redirect
//        resp.sendRedirect(req.getContextPath());
        resp.sendRedirect(req.getContextPath() + "/");


    }
}

