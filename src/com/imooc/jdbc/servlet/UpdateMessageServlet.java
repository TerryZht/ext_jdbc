package com.imooc.jdbc.servlet;

import com.imooc.jdbc.bean.Message;
import com.imooc.jdbc.service.MessageService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UpdateMessageServlet extends HttpServlet {
    private MessageService messageService;

    @Override
    public void init() throws ServletException {
        messageService = new MessageService();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long id = Long.valueOf(req.getParameter("id"));
        long userid = Long.valueOf(req.getParameter("userid"));
        String username = req.getParameter("username");
        Date createTime = null;
        try {
            createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(req.getParameter("createTime"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String title = req.getParameter("title");
        String content = req.getParameter("content");
        messageService.updateMsg(id,userid,title,content);
        Message message = new Message(id,userid,username, title,content,createTime);
        req.setAttribute("upMsg",message);
        req.getRequestDispatcher("/editMessagePromot.do").forward(req,resp);
    }

    @Override
    public void destroy() {
        messageService =null;
    }
}
