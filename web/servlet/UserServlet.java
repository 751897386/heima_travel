package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.impl.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import cn.itcast.travel.util.MailUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {

    UserService userService=new UserServiceImpl();
    /**
     * 注册mode
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String check = request.getParameter("check");
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");

        if(checkcode_server==null || !checkcode_server.equalsIgnoreCase(check))
        {
            ResultInfo resultInfo=new ResultInfo();
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("验证码错误");
            //response.setContentType("application/json;charset=utf-8");
            /*response.setCharacterEncoding("utf-8");
            String jsonIn = objectMapper.writeValueAsString(resultInfo);
            response.getWriter().write(jsonIn);*/
            writeValueOri(resultInfo,response);
            return;
        }
        User user=new User();
        Map<String, String[]> parameterMap = request.getParameterMap();
        try {
            BeanUtils.populate(user,parameterMap);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        boolean registerFlag=userService.register(user);
        ResultInfo resultInfo=new ResultInfo();
        if(registerFlag)
        {
            resultInfo.setFlag(true);
        }else {
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("注册失败");
        }

        //ObjectMapper objMapper=new ObjectMapper();
        //String jsonIn = objMapper.writeValueAsString(resultInfo);
        //response.setContentType("application/json;charset=utf-8");
        //response.setCharacterEncoding("utf-8");
        //response.getWriter().write(jsonIn);
        //objMapper.writeValue(response.getOutputStream(),resultInfo);
        // response.getOutputStream().write(jsonIn.getBytes());
        writeValueOri(resultInfo,response);
    }

    /**
     * 重发激活邮件mode
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void reSend(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ResultInfo resultInfo=new ResultInfo();
        if(request.getParameter("username")==null || request.getParameter("password")==null){
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("请输入正确的用户名或密码以保证邮件发送成功1");

            writeValueOri(resultInfo,response);return;
        }
        User user=userService.resend(request.getParameter("username"),request.getParameter("password"));
        if(user==null||user.getEmail()==null)
        {
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("请输入正确的用户名或密码以保证邮件发送成功2");

            writeValueOri(resultInfo,response);return;
        }
        MailUtils.sendMail(user.getEmail(),
                "点击<a href='http://localhost:9090/travel/user/activeUser?code="+user.getCode()+"'>此处</a>以验证您的帐号",
                "Recognize");
        resultInfo.setFlag(true);
        writeValueOri(resultInfo,response);
    }

    /**
     * 登录mode
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user=userService.login(request.getParameter("username"),request.getParameter("password"));
        ResultInfo rsInfo=new ResultInfo();
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");

        session.setAttribute("user_main",user);
        Cookie atrsCookie=new Cookie("JSESSIONID",session.getId());
        atrsCookie.setMaxAge(2*3600);
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

        writeValueOri(rsInfo,response);
    }

    /**
     * 首页查找用户名mode
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user=null;user=(User) session.getAttribute("user_main");
        /*if(user==null){
            System.out.println("main_user在session中为null");
        }*/
       writeValueOri(user,response);
    }

    /**
     * 退出mode
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void exit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().invalidate();
        response.sendRedirect("../login.html");
    }

    /**
     * 邮件激活mode
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void activeUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean flag=false;
        String code = request.getParameter("code");
        String msg;
        if(code==null)
        {
            System.out.println("Wrong!");
            return;
        }
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
