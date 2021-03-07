package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.impl.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import cn.itcast.travel.util.MailUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/reSendServlet")
public class reSendServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setCharacterEncoding("utf-8");
        ResultInfo resultInfo=new ResultInfo();
        UserService userService=new UserServiceImpl();
        ObjectMapper objectMapper=new ObjectMapper();
        if(request.getParameter("username")==null || request.getParameter("password")==null){
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("请输入正确的用户名或密码以保证邮件发送成功1");

            objectMapper.writeValue(response.getOutputStream(),resultInfo);return;
        }
        User user=userService.resend(request.getParameter("username"),request.getParameter("password"));
        if(user==null||user.getEmail()==null)
        {
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("请输入正确的用户名或密码以保证邮件发送成功2");

            objectMapper.writeValue(response.getOutputStream(),resultInfo);return;
        }
        MailUtils.sendMail(user.getEmail(),
                "点击<a href='http://localhost:9090/travel/activeUserServlet?code="+user.getCode()+"'>此处</a>以验证您的帐号",
                "Recognize");
        resultInfo.setFlag(true);
        objectMapper.writeValue(response.getOutputStream(),resultInfo);
    }
}
