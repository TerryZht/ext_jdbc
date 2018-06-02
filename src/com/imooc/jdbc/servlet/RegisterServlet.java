package com.imooc.jdbc.servlet;

import com.imooc.jdbc.bean.User;
import com.imooc.jdbc.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegisterServlet  extends HttpServlet{
    private UserService userService;

    @Override
    public void init() throws ServletException {
        super.init();
        userService=new UserService();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        String cpassword = req.getParameter("cpassword");
        if(cpassword.equals(password)){
            if(userService.reg(username,password)){
                User user = userService.login(username, password);
                req.getSession().setAttribute("user", user);
                req.getRequestDispatcher("/message/list.do").forward(req, resp);
            }else {
                req.getRequestDispatcher("/regPrompt.do").forward(req, resp);
            }
        }else {
            req.getRequestDispatcher("/regPrompt.do").forward(req, resp);
        }

    }
}
