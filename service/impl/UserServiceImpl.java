package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.impl.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;

public class UserServiceImpl implements UserService {

    UserDao userDao =new UserDaoImpl();
    @Override
    public boolean register(User user) {
        User _user=userDao.findByUserName(user);
        if(_user!=null)
        {
            return false;
        }
            user.setStatus("N");
            user.setCode(UuidUtil.getUuid());
            userDao.saveUser(user);
        MailUtils.sendMail(user.getEmail(),"点击<a href='http://localhost:9090/travel/user/activeUser?code="+user.getCode()+"'>此处</a>以验证您的帐号","Recognize");
            return true;
    }

    @Override
    public boolean active(String code) {
        User user=userDao.findByUserCode(code);
        if(user!=null)
        {
            userDao.updateStatus(user);
            return true;
        }
        return false;
    }

    @Override
    public User login(String username, String password) {
        return userDao.login(username,password);
    }

    @Override
    public User resend(String username, String password) {
        return userDao.login(username,password);
    }
}
