package cn.itcast.travel.dao.impl;

import cn.itcast.travel.domain.Route;

import java.util.List;

public interface RouteDao {

    public Route findRoute_Detail(int rid);

    public int findTotalCount(int cid, String rname);

    public List<Route> findByPage(int cid,int startInd,int pageLimited,String rname);
}
