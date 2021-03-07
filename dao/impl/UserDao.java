package cn.itcast.travel.dao.impl;

import cn.itcast.travel.domain.User;

public interface UserDao {
    User findByUserName(User user);

    void saveUser(User user);

    User findByUserCode(String code);

    void updateStatus(User user);

    User login(String username, String password);
}
