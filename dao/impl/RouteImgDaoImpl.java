package cn.itcast.travel.dao.impl;

import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class RouteImgDaoImpl implements RouteImgDao {
    JdbcTemplate template=new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public List<RouteImg> findImgList(int rid) {
        String sql="select * from tab_route_img where rid = ?";
        List<RouteImg> ImgList = template.query(sql, new BeanPropertyRowMapper<RouteImg>(RouteImg.class), rid);
        return ImgList;
    }
}
