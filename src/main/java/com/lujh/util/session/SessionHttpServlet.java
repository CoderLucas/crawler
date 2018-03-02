package com.lujh.util.session;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by lujianhao on 2018/3/2.
 */
public class SessionHttpServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");
        HttpSession session = req.getSession();
        String sessionId = session.getId();
        if (session.isNew()) {
            resp.getWriter().print("session创建成功，session的id是：" + sessionId);
        } else {
            resp.getWriter().print("服务器已经存在该session了，session的id是：" + sessionId);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
