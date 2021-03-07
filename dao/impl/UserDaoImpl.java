package cn.itcast.travel.dao.impl;

import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserDaoImpl implements UserDao {

    JdbcTemplate template=new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public User findByUserName(User user) {
        User backUser= null;
        try {
            String sql="select * from tab_user where username = ?";
            backUser = template.queryForObject(sql,new BeanPropertyRowMapper<User>(User.class),user.getUsername());
        } catch (DataAccessException e) {
        }

        return backUser;
    }

    @Override
    public void saveUser(User user) {
        String sql="insert into tab_user(username,password,name,birthday,sex,telephone,email,status,code) values (?,?,?,?,?,?,?,?,?)";
        template.update(sql, user.getUsername(), user.getPassword(), user.getName(), user.getBirthday(), user.getSex(), user.getTelephone(), user.getEmail(),user.getStatus(),user.getCode());
    }

    @Override
    public User findByUserCode(String code) {
        String sql="select * from tab_user where code = ?";
        User user_back=null;
        try {
           user_back = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), code);//Ctrl+Alt+t
        }catch (DataAccessException e){
            e.getStackTrace();
        }
        return user_back;
    }

    @Override
    public void updateStatus(User user) {
        String sql="update tab_user set status='Y' where uid = ?";
        template.update(sql,user.getUid());
    }

    @Override
    public User login(String username, String password) {
        String sql="select * from tab_user where username= ? and password= ?";
        User user=null;
        try {
            user=template.queryForObject(sql,new BeanPropertyRowMapper<User>(User.class),username,password);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return user;
    }
}

