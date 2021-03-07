package cn.itcast.travel.dao.impl;

import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;

public class FavoriteDaoImpl implements FavoriteDao {
    JdbcTemplate template=new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public Favorite findFavorite(int rid, int uid) {
        Favorite favorite=null;
        try {
            String sql = "select * from tab_favorite where rid = ? and uid = ?";
            favorite = template.queryForObject(sql, new BeanPropertyRowMapper<Favorite>(Favorite.class), rid, uid);
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return favorite;
    }

    @Override
    public int getFavoriteCount(int rid) {
        String sql="select count(*) from tab_favorite where rid = ?";
        int favoCount = template.queryForObject(sql, Integer.class, rid);
        return favoCount;
    }

    @Override
    public void change(int rid, int uid) {
        /*String sql="insert into tab_favorite values( ? , ? , ? )";
        template.update(sql,rid,new Date(),uid);*/
        String sql="select count(*) from tab_favorite where rid=? and uid=?";
        int favoriteCount = template.queryForObject(sql, Integer.class, rid, uid);
        String sqln;
        if(favoriteCount==0)
        {
            sqln="insert into tab_favorite values( ? , ? , ? )";
            template.update(sqln,rid,new Date(),uid);
        }else {
            sqln="delete from tab_favorite where rid=? and uid=?";
            template.update(sqln,rid,uid);
        }
    }
}
