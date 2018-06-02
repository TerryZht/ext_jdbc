package com.imooc.jdbc.servlet;

import com.imooc.jdbc.bean.Message;
import com.imooc.jdbc.service.MessageService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DeleteMyMSGServlet extends HttpServlet{
    private MessageService messageService;

    @Override
    public void init() throws ServletException {
        messageService = new MessageService();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        long id = Long.valueOf(req.getParameter("id"));
        long userid = Long.valueOf(req.getParameter("userId"));
        messageService.deleteMsg(id, userid, username);
        resp.sendRedirect("/my/message/list.do");
    }

    @Override
    public void destroy() {
        messageService=null;
    }
}
