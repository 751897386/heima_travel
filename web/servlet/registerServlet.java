package cn.itcast.travel.web.servlet;

import cn.itcast.travel.dao.impl.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.impl.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/registerServlet")
public class registerServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
                response.setCharacterEncoding("utf-8");
            ObjectMapper objMapper=new ObjectMapper();
            String jsonIn = objMapper.writeValueAsString(resultInfo);
            response.getWriter().write(jsonIn);
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
        UserService uService=new UserServiceImpl();
        boolean registerFlag=uService.register(user);
        ResultInfo resultInfo=new ResultInfo();
        if(registerFlag)
        {
            resultInfo.setFlag(true);
        }else {
            resultInfo.setFlag(false);
            resultInfo.setErrorMsg("注册失败");
        }

        ObjectMapper objMapper=new ObjectMapper();
        //String jsonIn = objMapper.writeValueAsString(resultInfo);
        //response.setContentType("application/json;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        //response.getWriter().write(jsonIn);
        objMapper.writeValue(response.getOutputStream(),resultInfo);
       // response.getOutputStream().write(jsonIn.getBytes());
    }
}
