package ru.itmo.wp.servlet;

import ru.itmo.wp.util.ImageUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.Random;

public class CaptchaFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession session = request.getSession();
        if (session.getAttribute("secure") == "true") {
            super.doFilter(request, response, chain);
            return;
        }

        if (request.getMethod().equals("POST")) {
            if (request.getRequestURI().equals("/captcha")) {
                String ans = (String) session.getAttribute("ans");
                if (ans == null) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                    return;
                }
                if (request.getParameter("ans").equals(ans)) {
                    session.setAttribute("secure", "true");
                    response.sendRedirect(request.getContextPath() + "index.html");
                    return;
                }
            } else {
                super.doFilter(request, response, chain);
                return;
            }
        }

        if (request.getMethod().equals("GET") &&
                (request.getRequestURI().equals("/captcha.png") || request.getRequestURI().equals("/favicon.ico"))) {
            String ans = (String) session.getAttribute("ans");
            if (ans == null) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                return;
            }
            response.getOutputStream().write(ImageUtils.toPng(ans));
            response.setContentType("image/png");
            return;
        }

        Random rand = new Random();
        int ans = rand.nextInt(900) + 100;
        session.setAttribute("ans", Integer.toString(ans));

        response.setContentType("text/html");
        OutputStream outputStream = response.getOutputStream();
        File captcha = new File("C:\\Users\\Aleksandr\\work\\Workplace\\WEB\\3.4\\src\\main\\webapp\\static\\captcha.html");
        Files.copy(captcha.toPath(), outputStream);
    }
}
