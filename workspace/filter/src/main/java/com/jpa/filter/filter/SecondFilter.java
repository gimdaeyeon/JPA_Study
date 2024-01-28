package com.jpa.filter.filter;

import jakarta.servlet.*;

import java.io.IOException;

public class SecondFilter implements Filter {

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        System.out.println("SecondFilter의 요청이 지나간다.");
        chain.doFilter(req, resp);
        System.out.println("SecondFilter의 응답이 지나간다.");
    }
}
