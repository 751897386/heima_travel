package cn.itcast.travel.service.impl;

import cn.itcast.travel.domain.User;

public interface UserService {

    boolean register(User user);

    boolean active(String code);

    User login(String username, String password);

    User resend(String username, String password);
}
