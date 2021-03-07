package cn.itcast.travel.service.impl;

public interface FavoriteService {
    boolean findFavorite(String rid, int uid);

    void change(String rid, int uid);

}
