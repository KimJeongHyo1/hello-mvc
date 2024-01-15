package com.sh.mvc.admin.contorller;

import com.sh.mvc.common.HelloMvcUtils;
import com.sh.mvc.member.model.entity.Member;
import com.sh.mvc.member.model.service.MemberService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@WebServlet("/admin/searchMember")
public class _AdminSearchMemberServlet extends HttpServlet {
    private MemberService memberService = new MemberService();

    /**
     * mybatis에서는 식별자(컬럼명, 테이블명)를 동적으로 작성할 수 있다. ${식별자}
     * - (PreparedStatement에는 없음)
     *
     * select * from member where id like?
     * select * from member where name like?
     * select * from member where email like?
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. 사용자 입력값 가져오기
        int page = 1;
        int limit = 10;

        try {
            page = Integer.parseInt(req.getParameter("page")); /* ctrl, alt, t */
        } catch (NumberFormatException ignored) { }

        String searchType = req.getParameter("search-type");
        String searchKeyword = req.getParameter("search-keyword");

        Map<String, Object> param = new HashMap<>(); /* 맵으로 묶어줘도 됌 */
        param.put("searchType", searchType);
        param.put("searchKeyword", searchKeyword);
        param.put("page", page);
        param.put("limit", limit);
        System.out.println(param);


        // 2. 업무로직
        // content영역
        List<Member> members = memberService.searchMember(param);
        System.out.println(members);
        req.setAttribute("members", members);

        // pagebar영역
        int totalCount = memberService.getTotalCount(param); // 검색조건에 맞는 총 회원수
        String url = req.getRequestURI() + "?search-type=" + searchType + "&search-keyword=" + searchKeyword;
        String pagebar = HelloMvcUtils.getPagebar(page, limit, totalCount, url);
        req.setAttribute("pagebar", pagebar);

        // 3. view단 처리
        req.getRequestDispatcher("/WEB-INF/views/admin/memberList.jsp").forward(req, resp);
    }
}