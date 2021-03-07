package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.impl.CategoryDao;
import cn.itcast.travel.dao.impl.CategoryDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService {
    CategoryDao categoryDao=new CategoryDaoImpl();

    @Override
    public List<Category> findAll() {
        Jedis jedis= JedisUtil.getJedis();
       //Set<String> category = jedis.zrange("category", 0, -1);
        Set<Tuple> category = jedis.zrangeWithScores("category", 0, -1);
        List<Category> allCates=null;
        if(category==null||category.size()==0)
        {
            System.out.println("search from database");
            allCates = categoryDao.findAll();
            for (int i = 0; i <allCates.size(); i++) {
                jedis.zadd("category",allCates.get(i).getCid(),allCates.get(i).getCname());
            }
        }else
        {
            System.out.println("search from redis");
            allCates=new ArrayList<Category>();
            /*int idCounter=1;
            for (String s : category) {
                Category atrs=new Category();
                atrs.setCid(idCounter++);
                atrs.setCname(s);
                allCates.add(atrs);
            }*/
            for ( Tuple tuple: category) {
                Category atrs=new Category();
                atrs.setCid((int)tuple.getScore());
                atrs.setCname(tuple.getElement());
                allCates.add(atrs);
            }
        }
        jedis.close();
        return allCates;
    }
}
