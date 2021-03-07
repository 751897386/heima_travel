package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.impl.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/activeUserServlet")
public class activeUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean flag=false;
        String code = request.getParameter("code");
        String msg;
        if(code==null)
        {
            System.out.println("Wrong!");
            return;
        }
        UserService userService=new UserServiceImpl();
        flag=userService.active(code);
        if(!flag)
        {
            msg="授权码验证失败，请联系客服！";
        }else {
            msg="授权码验证成功，请点击<a href=\"login.html\">登录</a>";
        }
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(msg);
    }
}
