package cn.itcast.travel.service.impl;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;

public interface RouteService {

    PageBean<Route> pageQuery(int cid,int currentPage,int pageLimited, String rname);

    Route findRouteDetail(String rid);
}
