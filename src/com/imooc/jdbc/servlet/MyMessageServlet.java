package com.imooc.jdbc.servlet;

import com.imooc.jdbc.bean.Message;
import com.imooc.jdbc.bean.User;
import com.imooc.jdbc.service.MessageService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class MyMessageServlet extends HttpServlet {
    private MessageService messageService;
    @Override
    public void init() throws ServletException {
        messageService = new MessageService();
    }
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pageStr = req.getParameter("page");//当前页码
        User user = (User) req.getSession().getAttribute("user");
        String username=null;
        try {
            username = user.getName();
        }catch (Exception e){
            e.printStackTrace();
            req.getRequestDispatcher("/login.do").forward(req,resp);
        }
        int page = 1;//页码默认值为1
        if (null != pageStr && (!"".equals(pageStr))) {
            try {
                page = Integer.parseInt(pageStr);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        List<Message> messages = messageService.getMyMessages(page, 5, username);//分页查询全部当前用户信息
        int count = messageService.countMessages();
        int last = count % 5 == 0 ? (count / 5) : ((count / 5) + 1);
        req.setAttribute("last", last);
        req.setAttribute("messages", messages);
        req.setAttribute("page", page);
        req.getRequestDispatcher("/WEB-INF/views/biz/mymessage_list.jsp").forward(req, resp);
    }

    @Override
    public void destroy() {
        messageService=null;
    }
}
