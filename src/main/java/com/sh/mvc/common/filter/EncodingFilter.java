package com.sh.mvc.common.filter;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 초기화파라미터
 * - servlet/filter등에서 환경변수처럼 사용가능
 * - web.xml에서 지정하거나, @WebFilter안에서 지정하면 됌
 */
//@WebFilter("/*")
@WebFilter(value = "/*", initParams = {
        @WebInitParam(name = "encoding", value = "utf-8")
} )
public class EncodingFilter extends HttpFilter {

    private String encoding;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.encoding = filterConfig.getInitParameter("encoding");
    }

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 전처리(Servlet 가기 전)
        request.setCharacterEncoding("utf-8");
        System.out.println("utf-8 적용 완료❗");
        super.doFilter(request, response, chain);
    }
}
