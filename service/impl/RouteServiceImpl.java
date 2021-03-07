package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.impl.*;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.domain.Seller;

import java.util.List;

public class RouteServiceImpl implements RouteService {

    RouteDao routeDao=new RouteDaoImpl();

    RouteImgDao routeImgDao=new RouteImgDaoImpl();

    SellerDao sellerDao=new SellerDaoImpl();

    FavoriteDao favoriteDao=new FavoriteDaoImpl();
    @Override
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageLimited, String rname) {
        PageBean<Route> routePB=new PageBean<Route>();
        routePB.setCurrentPage(currentPage);

        routePB.setPageLimited(pageLimited);

        int totalCount=routeDao.findTotalCount(cid,rname);
        routePB.setTotalContent(totalCount);

        List<Route> eleList=routeDao.findByPage(cid,(currentPage-1)*pageLimited ,pageLimited,rname);
        routePB.setEleList(eleList);

        int totalPage=totalCount % pageLimited == 0 ? totalCount/pageLimited : totalCount/pageLimited + 1;
        if(totalPage==0)
            totalPage=1;
        routePB.setTotalPage(totalPage);
        return routePB;
    }

    @Override
    public Route findRouteDetail(String rid) {

        Route route= routeDao.findRoute_Detail(Integer.parseInt(rid));

        List<RouteImg> imgList = routeImgDao.findImgList(Integer.parseInt(rid));
        route.setRouteImgList(imgList);

        Seller seller = sellerDao.findSeller(route.getSid());
        route.setSeller(seller);

        int count=favoriteDao.getFavoriteCount(Integer.parseInt(rid));
        route.setCount(count);

        return route;
    }
}
