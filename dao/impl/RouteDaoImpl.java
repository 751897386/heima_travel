package cn.itcast.travel.dao.impl;

import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class RouteDaoImpl implements RouteDao {
    private JdbcTemplate template=new JdbcTemplate(JDBCUtils.getDataSource());


    @Override
    public int findTotalCount(int cid,String rname) {
        /*String sql="select count(*) from tab_route where cid = ?";
                return template.queryForObject(sql,Integer.class,cid);*/
        String sql="select count(*) from tab_route where 1=1 ";
        StringBuilder sBuilder=new StringBuilder(sql);
        List<Object> answerList=new ArrayList<Object>();
        if(cid>0)
        {
            sBuilder.append(" and cid = ? ");
            answerList.add(cid);
        }
        if(rname!=null&&!("".equals(rname.trim())))
        {
            sBuilder.append(" and rname like ? ");
            answerList.add("%"+rname+"%");
        }
        sql=sBuilder.toString();
        return template.queryForObject(sql,Integer.class,answerList.toArray());

    }

    @Override
    public List<Route> findByPage(int cid, int startInd, int pageLimited,String rname) {
        /*String sql="select * from tab_route where cid= ? limit ? , ?";
        return template.query(sql,new BeanPropertyRowMapper<Route>(Route.class),cid,startInd,pageLimited);*/

        String sql="select * from tab_route where 1=1 ";
        StringBuilder sBuilder=new StringBuilder(sql);
        List answerList=new ArrayList();
        if(cid>0)
        {
            sBuilder.append(" and cid = ? ");
            answerList.add(cid);
        }
        if((null!=rname) && (!("".equals(rname.trim()))))
        {
            /*System.out.println(rname+"不为空");
            System.out.println(rname.length());*/
            sBuilder.append(" and rname like ? ");
            answerList.add("%"+rname+"%");
        }
        sBuilder.append(" limit ? , ? ");
        answerList.add(startInd);
        answerList.add(pageLimited);
        sql=sBuilder.toString();
        return template.query(sql,new BeanPropertyRowMapper<Route>(Route.class),answerList.toArray());
    }


    @Override
    public Route findRoute_Detail(int rid) {
        String sql="select * from tab_route where rid = ?";
        Route route = template.queryForObject(sql, new BeanPropertyRowMapper<Route>(Route.class), rid);
        return route;
    }
}
