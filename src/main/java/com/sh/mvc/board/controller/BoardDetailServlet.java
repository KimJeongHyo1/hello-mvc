package com.sh.mvc.board.controller;

import com.sh.mvc.board.model.exception.BoardException;
import com.sh.mvc.board.model.service.BoardService;
import com.sh.mvc.board.model.vo.BoardVo;
import com.sh.mvc.common.HelloMvcUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *  XSS 공격
 *  - Cross-site Scripting 공격
 *  - 악성스크립트를 웹페이지 삽입해서 클라이언트 개인정보를 탈취하는 공격법
 *  - script태그로 구성된 내용을 필터링없이 그대로 화면에 출력할때 취약할수 있다
 *  - Escape Html처리를 통해서 사용자입력값이 html요소로 작동하지 못하게 해야 한다
 */
@WebServlet("/board/boardDetail")
public class BoardDetailServlet extends HttpServlet {
    private BoardService boardService = new BoardService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            // 1. 사용자 입력값
            long id = Long.parseLong(req.getParameter("id"));
            System.out.println(id);

            // 2. 업무로직
            // 조회수 관련처리
            Cookie[] cookies = req.getCookies();
            List<String> boardCookieValues = getBoardCookieValues(cookies);
    //        boolean hasRead = false; // 일단 안읽은걸로 확인
    //        Arrays.asList(value.split("/")); // Arrays.asList = immutable한 요소 반환
            boolean hasRead = boardCookieValues.contains(String.valueOf(id)); // 현재 게시글 읽음여부
            System.out.println(hasRead); // true는 이미 읽었음, false는 처음 읽음

            // 조회
            BoardVo board = boardService.findById(id, hasRead);
            System.out.println(board);

            // xss공격대비 escape Html처리
            String safeHtml = HelloMvcUtils.escapeHtml(board.getContent());

            // 개행문자(\n)을 <br>로 바꾸는법 (db바뀌는거아님, 뿌리기전에 바꾸는것)
            board.setContent(HelloMvcUtils.convertLineFeedToBr(safeHtml));
            req.setAttribute("board", board);

            // 응답 쿠키 생성 (글을 처음 읽을때만)
            // 만료시간 쿠키종류
            // - session cookie -1 지정한 경우, session이 만료되면 쿠키 자동 삭제
            // - persistant cookie n초 지정한 경우
            if (!hasRead) {
                boardCookieValues.add(String.valueOf(id)); // 현재 게시글 id를 추가
                String value = String.join("/", boardCookieValues); // [12, 34, 56](몇번글읽었는지) -> "12/34/56"

                Cookie cookie = new Cookie("board", value);
                cookie.setMaxAge(365 * 24 * 60 * 60); // 음수인 경우 session종료시 삭제, 0인 경우는 즉시 삭제됨/ 조회기간
                cookie.setPath(req.getContextPath() + "/board/boardDetail"); // 지정한 경로일때만 클라이언트에서 서버로 쿠키 전송
                resp.addCookie(cookie); // cookie는 여러번 등록 가능
            }

            // 3. forward
            req.getRequestDispatcher("/WEB-INF/views/board/boardDetail.jsp").forward(req, resp);
        } catch (Exception e) {
            // 예외 전환해서 던지기 : 사용자친화적 메세지, 원인예외 wrapping
            throw new BoardException("게시글 상세보기 오류", e);
        }
    }

    private List<String> getBoardCookieValues(Cookie[] cookies) {
        List<String> boardCookieValues = new ArrayList<>();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                String name = cookie.getName();
                String value = cookie.getValue();
                System.out.println(name + " = " + value);
                if ("board".equals(name)) {
                    String[] temp = value.split("/"); // 23, 24, 56
                    for (String _id : temp) {
                        boardCookieValues.add(_id);
                    }
                }
            }
        }
        return boardCookieValues;
    }
}