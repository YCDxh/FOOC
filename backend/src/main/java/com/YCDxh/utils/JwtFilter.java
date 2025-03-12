package com.YCDxh.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Slf4j
@WebFilter(filterName = "jwtFilter", urlPatterns = "/api/test/*")
public class JwtFilter extends OncePerRequestFilter implements Filter {
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        if (token == null || JwtUtils.getUsernameFromToken(token) != null) {
//            response.sendRedirect("http://localhost:8080/user/login");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
        }
        log.info("JwtFilter doFilterInternal");
        chain.doFilter(request, response);


    }
}
  