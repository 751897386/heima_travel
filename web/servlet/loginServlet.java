package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.impl.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/loginServlet")
public class loginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=utf-8");
        User user;

        UserService userService=new UserServiceImpl();
        user=userService.login(request.getParameter("username"),request.getParameter("password"));
        ResultInfo rsInfo=new ResultInfo();
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");

        session.setAttribute("user_main",user);
        Cookie atrsCookie=new Cookie("JSESSIONID",session.getId());
        atrsCookie.setMaxAge(24*3600);
        response.addCookie(atrsCookie);

        if(!request.getParameter("check").equalsIgnoreCase(checkcode_server))
        {
            rsInfo.setFlag(false);
            rsInfo.setErrorMsg("请输入正确的验证码");
        }else
        if(user==null)
        {
            rsInfo.setFlag(false);
            rsInfo.setErrorMsg("用户名或密码错误");
        }else
        if(user!=null && "N".equals(user.getStatus()))
        {
            rsInfo.setFlag(false);
            rsInfo.setErrorMsg("账号邮箱未验证，请进入邮箱验证\n或重新<a href='javascript:sendFunc()'>发送激活邮件</a>");
        }else
        if(user!=null && "Y".equals(user.getStatus()))
        {
            rsInfo.setFlag(true);
        }

        ObjectMapper objectMapper=new ObjectMapper();
        objectMapper.writeValue(response.getOutputStream(),rsInfo);
    }
}
