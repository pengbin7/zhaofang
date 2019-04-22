package com.pb.service.user;

import com.pb.entity.User;

/**
 * @Author pengbin
 * @date 2019/4/17 17:01
 */
public interface IUserService {

    public User findUserByName(String name);

}
