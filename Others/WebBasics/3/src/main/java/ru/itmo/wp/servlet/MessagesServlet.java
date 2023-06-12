package ru.itmo.wp.servlet;

import com.google.gson.Gson;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.*;

public class MessagesServlet extends HttpServlet {
    private final List<Object> messages = Collections.synchronizedList(new ArrayList<>());

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String uri = request.getRequestURI();
        HttpSession session = request.getSession();
        response.setContentType("application/json");
        String user, text;
        switch (uri) {
            case "/message/auth":
                user = request.getParameter("user");
                if (user != null) {
                    session.setAttribute("user", user);
                }
                user = (String) session.getAttribute("user");
                if (user == null) {
                    user = "";
                }
                String json = new Gson().toJson(user);
                response.getWriter().print(json);
                response.getWriter().flush();
                break;
            case "/message/findAll":
                json = new Gson().toJson(messages);
                response.getWriter().print(json);
                response.getWriter().flush();
                break;
            case "/message/add":
                user = (String) session.getAttribute("user");
                text = request.getParameter("text");
                if (user == null || text == null || text.trim().isEmpty()) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                    return;
                }
                messages.add(Map.of("user", user, "text", text));
                break;
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}