package com.white.stratego.stratego.Market.service;

import com.white.stratego.stratego.Market.model.User;

public interface UserService {
    void save(User user);

    User findByUsername(String username);
}
