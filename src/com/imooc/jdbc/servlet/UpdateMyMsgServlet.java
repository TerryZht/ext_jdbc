package com.imooc.jdbc.servlet;

import com.imooc.jdbc.bean.Message;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 修改我的信息
 */
public class UpdateMyMsgServlet extends HttpServlet{
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String title = req.getParameter("title");
        Date createTime = null;
        try {
             createTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(req.getParameter("createTime"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long userId = Long.valueOf(req.getParameter("userId"));
        long id = Long.valueOf(req.getParameter("id"));
        String content = req.getParameter("content");
        Message message = new Message(id,userId,username, title,content,createTime);
        System.out.println(message);
        req.setAttribute("upMsg",message);
        req.getRequestDispatcher("/editMessagePromot.do").forward(req,resp);
    }
}
