package com.white.stratego.stratego.market.service;

import com.white.stratego.stratego.market.model.User;

public interface UserService {
    void save(User user);

    User findByUsername(String username);
}
