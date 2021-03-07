package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.impl.FavoriteDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.domain.Favorite;

public class FavoriteServletImpl implements FavoriteService {

    FavoriteDao favoriteDao=new FavoriteDaoImpl();
    @Override
    public boolean findFavorite(String rid, int uid) {
        Favorite favorite = favoriteDao.findFavorite(Integer.parseInt(rid), uid);
        return favorite != null;//favorite为null则返回false
    }

    @Override
    public void change(String rid, int uid) {
        favoriteDao.change(Integer.parseInt(rid),uid);
    }
}
