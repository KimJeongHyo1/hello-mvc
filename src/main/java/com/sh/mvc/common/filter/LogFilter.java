package com.sh.mvc.common.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filter
 * - Servlet 전후처리를 담당하는 클래스
 * - 공통코드를 관리 : 인코딩처리, 인증/인가처리, 응답파일 압축 등
 *
 * Filter클래스를 만드는 방법
 * - javax servlet.Filter인터페이스를 구핸
 *  - doFilter(ServletRequest, ServletResponse, FilterChain) 오버라이드
 * - javax servlet.http.HttpFilter클래스를 상속
 * - doFilter(HttpServletRequest, HttpServletResponse, FilterChain) 오버라이드
 * - ServletRequest, ServletResponse부모타입을 상속한 HttpServletRequest, HttpServletResponse
 * - down-casting 할 필요없이 즉시 사용가능해서 편리함
 */

@WebFilter("/*") // 모든 filter지나가게 *사용
public class LogFilter extends HttpFilter implements Filter {

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 전처리 (요청직후)
        String uri = request.getRequestURI();
        String method = request.getMethod();
        System.out.println("======================================================");
        System.out.printf("%s %s\n", method, uri);
        System.out.println("------------------------------------------------------");

        // filterChain : filter묶음(여러 Filter를 그룹핑해서 관리)
        // - 다음 Filter가 있는 경우, 해당 Filter#doFilter를 호출
        // - 마지막 Filter인 경우, Servlet 호출

//        super.doFilter(request, response, chain); // chain.doFilter(request, response) / 있어야함.손대지말것
        chain.doFilter(request, response); // super 대신 이렇게 작성도 가능

        // 후처리 (응답직전)
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println(response.getStatus());
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println();
    }
}
