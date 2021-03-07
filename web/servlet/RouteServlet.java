package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.impl.FavoriteService;
import cn.itcast.travel.service.impl.FavoriteServletImpl;
import cn.itcast.travel.service.impl.RouteService;
import cn.itcast.travel.service.impl.RouteServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {

    RouteService routeService=new RouteServiceImpl();

    FavoriteService favoriteService=new FavoriteServletImpl();
    /**
     * 生成route展示
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cidStr = request.getParameter("cid");
        String currentPageStr = request.getParameter("currentPage");
        String pageLimitedStr = request.getParameter("pageLimited");
        String rname=request.getParameter("rname");
        //
        int cid=0;
        int currentPage;
        int pageLimited;
        if(cidStr!=null && cidStr.length()!=0 && !"null".equals(cidStr))
        {
            System.out.println("recipe");
            cid=Integer.parseInt(cidStr);
        }
        System.out.println("cidValue ="+cid);
        if(currentPageStr!=null && currentPageStr.length()!=0)
        {
            currentPage=Integer.parseInt(currentPageStr);
        }else {
            currentPage=1;
        }
        if(pageLimitedStr!=null && pageLimitedStr.length()!=0)
        {
            pageLimited=Integer.parseInt(pageLimitedStr);
        }else {
            pageLimited=5;
        }
        PageBean<Route> pageB = routeService.pageQuery(cid, currentPage, pageLimited,rname);
        this.writeValueOri(pageB,response);
    }


    /**
     * 生成route细节
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findRouteDetails(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        String rid = request.getParameter("rid");

        Route route=routeService.findRouteDetail(rid);

        this.writeValueOri(route,response);
    }

    /*登陆后的Session中的user相关键值对，键值为user_main*/
    public void favoriteSearch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user_main = (User) session.getAttribute("user_main");
        int uid=0;//未登录，uid为0
        if(user_main!=null)
            uid=user_main.getUid();
        String rid = request.getParameter("rid");
        boolean favorite_jud = favoriteService.findFavorite(rid, uid);
        this.writeValueOri(favorite_jud,response);
    }

    public void changeFavorite(HttpServletRequest request, HttpServletResponse response) throws  ServletException, IOException
    {
        String rid = request.getParameter("rid");
        User user = (User) request.getSession().getAttribute("user_main");
        if(user==null)
            return;
        else {
            favoriteService.change(rid,user.getUid());
        }
    }
}
