package cn.itcast.travel.dao.impl;

import cn.itcast.travel.domain.Favorite;

public interface FavoriteDao {
    Favorite findFavorite(int rid, int uid);

    int getFavoriteCount(int rid);

    void change(int rid, int uid);
}
